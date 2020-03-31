import { Component, OnInit, ViewChild } from '@angular/core';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { QuestionDTO } from '../dto/questiondto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  cols: any[];
  questionList: QuestionDTO[];
  loading: boolean;
  constructor(private http: BasehttpService, private router: Router) { }
  @ViewChild('dt') questionTable: any;
  ngOnInit(): void {
    this.loading = true;
    this.http.get(Constants.GET_FB_QUESTION_URL).subscribe((data) => {
      this.questionList = data;
      this.loading = false;
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
}


