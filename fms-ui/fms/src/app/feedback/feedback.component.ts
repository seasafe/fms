import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { BasehttpService } from '../common/basehttp.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Constants } from '../common/constants';
import { FeedbackTypeDTO } from '../dto/feedbacktypedto';
import { FeedbackDTO } from '../dto/feedbackdto';
import { QuestionDTO } from '../dto/questiondto';
import { ConvertionDTO } from '../dto/convertiondto';
import { QuestionsComponent } from '../admin/questions/questions.component';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit {

  feedbackForm: FormGroup;
  eventRefId: string;
  eventName: string;
  eventDesc: string;
  eventDate: string;
  userEmail: string;
  type: string;
  isFeedbackTaken: boolean;
  feedbackDTO: FeedbackDTO;
  questionList: QuestionDTO[];
  feedbacks: FeedbackDTO[] = [];
  multiAnsArray: string[] = [];

  constructor(private http: BasehttpService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {

      const base64Param = params.params;
      const decodedParam = window.atob(base64Param);
      const convertedObj = decodedParam.split('&').reduce((acc, cur) => {
        const pair = cur.split('=');
        const val = pair[1].trim().replace(/\+/gi, ' ');
        console.log('val - ' + val);
        acc[pair[0].trim()] = decodeURI(val);
        return acc;
      }, {});
      const convertedObject = convertedObj as ConvertionDTO;
      this.type = convertedObject.type;
      this.eventRefId = convertedObject.eventRefId;
      this.eventName = convertedObject.eventName;
      this.eventDate = convertedObject.eventDate;
      this.eventDesc = 'Feedback Request for ' + this.eventName + '  on ' + this.eventDate;

      this.http.post(Constants.VALIDATE_FB_SUBMISSION_URL, this.getFeedbackDTO()).subscribe((data) => {
        if (data) {
          this.router.navigate(['feedback-taken']);
        } else {

          this.getDisplayQuestions('Not-Participated');
        }
      });
    });


  }

  getDisplayQuestions(type: string = 'Participated'): void {
    this.http.get(Constants.GET_QUESTIONS_BY_TYPE_URL + '?type=' + type).subscribe((ques: QuestionDTO[]) => {
      this.questionList = ques;
    });
  }


  onSubmit() {
    console.log('submitted' + JSON.stringify(this.questionList));
    this.feedbacks.slice();
    this.questionList.forEach((q) => {
      const fb = new FeedbackDTO();
      fb.answer = q.selectedAnswer;
      fb.questionId = q.questionId;
      fb.eventRefId = this.eventRefId;
      this.feedbacks.push(fb);
    });
    this.http.post(Constants.SAVE_FEEDBACK_URL, this.feedbacks).subscribe((ids) => {
      if (ids) {
        console.log(ids);
        this.router.navigate(['feedback-success']);
      }
    });

  }
  resetAnswers() {
    console.log('reset');
  }
  getFeedbackDTO(): FeedbackDTO {
    this.feedbackDTO = new FeedbackDTO();
    this.feedbackDTO.employeeEmail = this.userEmail;
    this.feedbackDTO.eventRefId = this.eventRefId;
    return this.feedbackDTO;
  }

  onChange(value: string, ischecked: boolean, questionId: number) {
    console.log('value' + value + 'ischecked' + ischecked + 'questionId' + questionId);
    if (ischecked) {
      this.multiAnsArray.push(value);
      this.questionList.forEach(q => {
        if (q.questionId === questionId) {
          q.selectedAnswer = this.multiAnsArray.toString();
        }
      });
      //this.questionList[questionId].selectedAnswer = this.multiAnsArray.toString();
    } else {
      const index = this.multiAnsArray.indexOf(value, 0);
      if (index > -1) {
        this.multiAnsArray.splice(index, 1);
      }
    }
    console.log('this.multiAnsArray' + this.multiAnsArray);
  }

  resetForm(form: NgForm) {
    form.reset();
    this.questionList.forEach(q => {
      if (q.multiAnsAllowed) {
        q.selectedAnswer = null;
      }
    });
  }
}
