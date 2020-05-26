import { Component, OnInit, ViewChild } from '@angular/core';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { QuestionDTO } from '../dto/questiondto';
import { Router } from '@angular/router';
import { LazyLoadEvent } from 'primeng/api/public_api';
import { Pagination } from '../common/Pagination';
import { QuestionsComponent } from './questions/questions.component';
import { SearchPagination } from '../common/SearchPagination';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  cols: any[];
  questionList: QuestionDTO[];
  loading: boolean;
  totalRecords: number;
  constructor(private http: BasehttpService, private router: Router) { }
  @ViewChild('dt') questionTable: any;
  ngOnInit(): void {
    this.loading = true;
    this.http.get(Constants.GET_FB_QUESTION_URL).subscribe((data) => {
      this.questionList = data.content;
      this.totalRecords = data.totalElements;
      this.loading = false;
      console.log('retrieved results count = ' + this.questionList.length);
    });

    this.cols = [
      { field: 'question', header: 'Questions' },
      { field: 'totalAnswers', header: 'Total Answers' },
      { field: 'type', header: 'Feed Back Type' }
    ];
  }

  editQuestion(question: any) {
    this.router.navigate(['/question/' + question.questionId]);
  }
  clearFilters(): void {
    this.questionTable.reset();
  }

  loadLazy(event: LazyLoadEvent) {


    // in a real application, make a remote request to load data using state metadata from event
    // event.first = First row offset
    // event.rows = Number of rows per page
    // event.sortField = Field name to sort with
    // event.sortOrder = Sort order as number, 1 for asc and -1 for dec
    // filters: FilterMetadata object having field as key and filter value, filter matchMode as value
    const pagination = new SearchPagination<QuestionDTO>();
    pagination.size = event.rows;
    pagination.page = event.first / event.rows;
    if (event.sortField) {
      if (event.sortField === 'type') {
        event.sortField = 'feedbackType_' + event.sortField;
      }
      pagination.sort = event.sortField + ',' + ((event.sortOrder === 1) ? 'Asc' : 'Desc');
    }
    // else {
    //   pagination.sort = this.cols[0].field;
    // }

    if (event.filters) {
      console.log('filters', event.filters);
      const q = new QuestionDTO();
      q.question = event.filters.global?.value ? event.filters.global.value : event.filters.question?.value;
      q.total = event.filters.global?.value ? event.filters.global.value : event.filters.totalAnswers?.value;
      q.type = event.filters.global?.value ? event.filters.global.value : event.filters.type?.value;
      pagination.searchCriteria = q;
      if (event.filters.global) {
        pagination.isGlobalSearch = true;
      }
    }



    // imitate db connection over a network
    console.log(JSON.stringify(pagination));
    //this.loading = true;
    this.http.post(Constants.GET_FB_QUESTION_SEARCH_URL, pagination).subscribe((data) => {
      this.questionList = data.content;
      this.totalRecords = data.totalElements;
      //this.loading = false;
      console.log('retrieved results count = ' + this.questionList.length);
    });
  }
}


