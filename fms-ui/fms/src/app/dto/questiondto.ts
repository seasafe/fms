import { FeedbackTypeDTO } from './feedbacktypedto';
import { AnswerDTO } from './answerdto';

export class QuestionDTO {
    questionId: number;
    question: string;
    questShortDesc: string;
    feedbackType: FeedbackTypeDTO;
    feedbackTypeId: number;
    type: string;
    totalAnswers: number;
    answers: AnswerDTO[];
    freeText: boolean;
    multiAnsAllowed: boolean;
    customQuestion: boolean;
}
