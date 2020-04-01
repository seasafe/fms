import { Component, OnInit } from '@angular/core';
import { FeedbackTypeDTO } from 'src/app/dto/feedbacktypedto';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { BasehttpService } from 'src/app/common/basehttp.service';
import { Constants } from 'src/app/common/constants';
import { QuestionDTO } from 'src/app/dto/questiondto';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.scss']
})
export class QuestionsComponent implements OnInit {

  pageTitle = 'Create Question';
  feedbackTypes: FeedbackTypeDTO[];
  questionForm: FormGroup;
  question: QuestionDTO;
  answers: FormArray;

  validationMessages = {
    questionShortDesc: { required: 'Question Short Description is required' },
    type: { required: 'Feedback Type is required' },
    question: { required: 'Question is required' },
    answer: { required: 'Answer is required' },
  };

  formErrors = {
    questionShortDesc: '',
    type: '',
    question: '',
    answer: ''
  };

  constructor(private fb: FormBuilder, private http: BasehttpService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.question = new QuestionDTO();
    this.http.get(Constants.GET_FB_TYPE_URL).subscribe((types: FeedbackTypeDTO[]) => {
      this.feedbackTypes = types;
    });
    this.question.questionId = +this.route.snapshot.paramMap.get('questionId');
    if (this.question.questionId !== 0) {
      this.http.get(Constants.GET_FB_QUESTION_URL + '/' + this.question.questionId).subscribe((ques: QuestionDTO) => {
        this.question = ques;
        this.pageTitle = 'Edit Question';
        this.setFormValues();
      });
    }
    this.questionForm = this.fb.group({
      questionShortDesc: ['', Validators.required],
      type: ['Participated', Validators.required],
      multiAnsAllowed: [false],
      freeText: [false],
      customQuestion: [false],
      question: ['', Validators.required],
      answers: this.fb.array([])
    });
    this.questionForm.valueChanges.subscribe((data) => {
      this.checkForValidationError(this.questionForm);
    });
  }

  setFormValues(): void {
    if (this.question.questionId) {
      this.questionForm.patchValue({
        questionShortDesc: this.question.questShortDesc,
        type: this.question.type,
        multiAnsAllowed: this.question.multiAnsAllowed,
        freeText: this.question.freeText,
        customQuestion: this.question.customQuestion,
        question: this.question.question,
        answers: this.getFormArrayValue()
      });
    }
  }

  getFormArrayValue(): FormArray {
    const control = this.questionForm.get('answers') as FormArray;
    this.question.answers.forEach(ans => {
      control.push(this.addAnswerFormGroup(ans.answer));
    });
    return control;
  }

  mapFormValues(): void {
    this.question.multiAnsAllowed = this.questionForm.get('multiAnsAllowed').value;
    this.question.type = this.questionForm.get('type').value;
    this.question.customQuestion = this.questionForm.get('customQuestion').value;
    this.question.freeText = this.questionForm.get('freeText').value;
    this.question.question = this.questionForm.get('question').value;
    this.answers = this.questionForm.get('answers') as FormArray;
    this.question.answers = this.answers.value;
    this.question.totalAnswers = this.answers.length;
    this.question.questShortDesc = this.questionForm.get('questionShortDesc').value;
  }


  addAnswerFormGroup(ans: string | ''): FormGroup {
    return this.fb.group({
      answer: [ans, Validators.required]
    });
  }
  addAnswer(): void {
    this.answers = this.questionForm.get('answers') as FormArray;
    this.answers.push(this.addAnswerFormGroup(''));
  }
  onSubmit(): void {
    this.mapFormValues();

    this.http.post(Constants.SAVE_QUESTION_URL, this.question).subscribe((data: QuestionDTO) => {
      this.questionForm.reset();
      console.log('Quesiton saved' + data.questionId);
      this.router.navigate(['/admin']);
    });
  }

  addAnswerClick(): void {
    (this.questionForm.get('answers') as FormArray).push(this.addAnswerFormGroup(''));
  }

  removeAnswer(idx: number): void {
    this.answers = this.questionForm.get('answers') as FormArray;
    this.answers.removeAt(idx);
  }

  deleteQuestion(): void {
    if (this.question && this.question.questionId !== 0) {
      this.http.delete(Constants.DELETE_QUESTION_URL + '/' + this.question.questionId).subscribe((ques: QuestionDTO) => {
        this.question = new QuestionDTO();
        this.questionForm.reset();
        this.router.navigate(['/admin']);
      },
        (err) => {
          console.log(err);
        }
      );
    } else {
      this.questionForm.reset();
      this.router.navigate(['/admin']);
    }
  }

  cancelQuestion(): void {
    this.questionForm.reset();
  }

  checkForValidationError(group: FormGroup = this.questionForm): void {
    Object.keys(group.controls).forEach((key: string) => {
      const abstractControl = group.get(key);
      this.formErrors[key] = '';
      if (abstractControl && !abstractControl.valid
        && (abstractControl.touched || abstractControl.dirty)) {
        const messages = this.validationMessages[key];
        for (const errorKey in abstractControl.errors) {
          if (errorKey) {
            this.formErrors[key] += messages[errorKey] + ' ';
          }
        }
      }
      if (abstractControl instanceof FormGroup) {
        this.checkForValidationError(abstractControl);
      }
    });
  }
}
