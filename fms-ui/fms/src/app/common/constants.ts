import { environment } from '../../environments/environment';

export class Constants {
    static DOMAIN_URL = environment.domain;
    static EMPLOYEE_BASE_API_URL = 'employees-api/';
    static EVENTS_BASE_API_URL = 'events-api/';
    static LOGIN_URL = Constants.DOMAIN_URL + 'api/login';
    static GET_EVENTS_URL = Constants.DOMAIN_URL + Constants.EVENTS_BASE_API_URL + 'events';
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
