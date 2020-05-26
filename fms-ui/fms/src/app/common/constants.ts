import { environment } from '../../environments/environment';

export class Constants {
    static DOMAIN_URL = environment.domain;
    static EMPLOYEE_BASE_API_URL = 'employees-api/';
    static EVENTS_BASE_API_URL = 'events-api/';
    static LOGIN_URL = Constants.DOMAIN_URL + 'api/login';
    static GET_EVENTS_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'events';
    static ASSIGN_PMO_URL = Constants.DOMAIN_URL + Constants.EMPLOYEE_BASE_API_URL + 'assignRole';
    static GET_PMO_URL = Constants.DOMAIN_URL + Constants.EMPLOYEE_BASE_API_URL + 'pmos';
    static GET_FB_QUESTION_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/questions';
    static GET_FB_QUESTION_SEARCH_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/questions/search';
    static GET_FB_TYPE_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/type';
    static SAVE_QUESTION_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/questions';
    static DELETE_QUESTION_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/questions';
    static VALIDATE_FB_SUBMISSION_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/validatefeedback';
    static GET_QUESTIONS_BY_TYPE_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback/questionsbytype';
    static SAVE_FEEDBACK_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'feedback';
    static SEND_EVENT_FEEDBACK_REQUEST_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'sendfeedbackrequest';
    static NOTIFICATION_OPTIONS = {
        autoClose: true,
        keepAfterRouteChange: false
    };
    static NOTIFICATION_AUTOCLOSE_OPTIONS = {
        autoClose: true,
        keepAfterRouteChange: false
    };
    static NOTIFICATION_STICKY_OPTIONS = {
        autoClose: false,
        keepAfterRouteChange: true
    };
    static NOTIFICATION_STIKY_AUTOCLOSE_OPTIONS = {
        autoClose: true,
        keepAfterRouteChange: true
    };



}
