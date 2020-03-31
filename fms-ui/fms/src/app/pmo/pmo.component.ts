import { Component, OnInit, ViewChild } from '@angular/core';
import { BasehttpService } from '../common/basehttp.service';
import { FormGroup } from '@angular/forms';
import { Constants } from '../common/constants';
import { PMODto } from '../dto/pmodto';
import { EmployeeDto } from '../dto/employeedto';
//import { MatTableDataSource } from '@angular/material/table';
//import { MatPaginator } from '@angular/material/paginator';
//import { MatSort, MatSortModule } from '@angular/material/sort';

@Component({
  selector: 'app-pmo',
  templateUrl: './pmo.component.html',
  styleUrls: ['./pmo.component.scss']
})
export class PmoComponent implements OnInit {


  errString: string;
  pmoList: EmployeeDto[];
  cols: any[];
  loading: boolean;
  //displayedColumns: string[] = ['email', 'firstName', 'lastName'];
  //dataSource = new MatTableDataSource<EmployeeDto>(this.pmoList);
  //@ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  //@ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private http: BasehttpService) { }

  ngOnInit(): void {
    this.loading = true;
    this.http.get(Constants.GET_PMO_URL).subscribe((data) => {
      this.pmoList = data.content;
      this.loading = false;
      //this.dataSource.data = this.pmoList;
      //setTimeout(() => {
      //  this.dataSource.sort = this.sort;
      //  this.dataSource.paginator = this.paginator;
      //});
    });

    this.cols = [
      { field: 'email', header: 'Email' },
      { field: 'firstName', header: 'First Name' },
      { field: 'lastName', header: 'Last Name' }
    ];
  }

  assignPMO(email: string, isRemove: boolean) {
    this.errString = null;
    if (email == null || email === '') {
      this.errString = 'Please enter valid email';
      return;
    }

    const pmoDto = new PMODto();
    pmoDto.email = email;
    pmoDto.isRemove = isRemove;
    pmoDto.role = 'PMO';
    this.http.post(Constants.ASSIGN_PMO_URL, pmoDto).subscribe(
      (data) => {
        console.log('Assigned PMO ', data);
        this.pmoList = data.content;
        //this.dataSource.data = this.pmoList;
      }
    );
  }
}
