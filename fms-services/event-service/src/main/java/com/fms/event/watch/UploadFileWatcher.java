/**
 * 
 */
package com.fms.event.watch;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fms.event.batch.EventSummaryBatchLauncher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Component
@Slf4j
public class UploadFileWatcher implements Runnable {

	@Value("${upload.files.dir}")
	private String fileWatcherRootDir;
	
	@Autowired
	EventSummaryBatchLauncher eventSummaryBatchLauncher;
	

	/**
	 * 
	 */
	public UploadFileWatcher() {
		super();
		this.keys = new HashMap<WatchKey, Path>();
	}

	protected List<FileListener> listeners = new ArrayList<>();
	protected File folder;
	protected static final List<WatchService> watchServices = new ArrayList<>();
	private final Map<WatchKey, Path> keys;

	public void watch() {
		if (folder.exists()) {
			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		}
	}

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Paths.get(folder.getAbsolutePath());
			walkAndRegisterDirectories(path,watchService);

			//path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
			watchServices.add(watchService);
			
			WatchKey key;
	        while ((key = watchService.take()) != null) {
	        	key.reset();
	        	path  = keys.get(key);
	            for (WatchEvent<?> event : key.pollEvents()) {
	                System.out.println(
	                  "Event kind:" + event.kind() 
	                    + ". File affected: " + event.context() + ".");
	                log.info("event {} trigger for : {}",event.kind().name(),event.context());
	    			notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
	            }
	            key.reset();
	        }
			
			
			/*
			 * boolean poll = true; while (poll) { poll = pollEvents(watchService); }
			 */
		} catch (IOException | InterruptedException | ClosedWatchServiceException e) {
			Thread.currentThread().interrupt();
		}
	}

	
	/**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir,WatchService watcher) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void walkAndRegisterDirectories(final Path start, WatchService watcher) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir,watcher);
                return FileVisitResult.CONTINUE;
            }
        });
    }
	
	
	/*
	 * protected boolean pollEvents(WatchService watchService) throws
	 * InterruptedException { WatchKey key = watchService.take(); Path path = (Path)
	 * key.watchable(); for (WatchEvent<?> event : key.pollEvents()) {
	 * WatchEvent.Kind<?> kind = event.kind();
	 * 
	 * @SuppressWarnings("unchecked") WatchEvent<Path> ev = (WatchEvent<Path>)
	 * event; Path fileName = ev.context();
	 * 
	 * log.info("event {} trigger for : {}",kind.name(),fileName);
	 * notifyListeners(kind, path.resolve((Path) event.context()).toFile()); }
	 * return key.reset(); }
	 */

	protected void notifyListeners(WatchEvent.Kind<?> kind, File file) {
		if(!file.isDirectory()) {
		FileEvent event = new FileEvent(file);
		if (kind == ENTRY_CREATE) {
			for (FileListener listener : listeners) {
				listener.onCreated(event,eventSummaryBatchLauncher);
			}
			
		} else if (kind == ENTRY_MODIFY) {
			for (FileListener listener : listeners) {
				listener.onModified(event,eventSummaryBatchLauncher);
			}
		} else if (kind == ENTRY_DELETE) {
			for (FileListener listener : listeners) {
				listener.onDeleted(event,eventSummaryBatchLauncher);
			}
		}
		}
	}

	public UploadFileWatcher addListener(FileListener listener) {
		listeners.add(listener);
		return this;
	}

	public UploadFileWatcher removeListener(FileListener listener) {
		listeners.remove(listener);
		return this;
	}

	public List<FileListener> getListeners() {
		return listeners;
	}

	public UploadFileWatcher setListeners(List<FileListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	public static List<WatchService> getWatchServices() {
		return Collections.unmodifiableList(watchServices);
	}
	
	@PostConstruct
	public void init(){
	     this.folder = new File(fileWatcherRootDir);
	     watch();
	     this.addListener(new FileAdapter());
	}
}
