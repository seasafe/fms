import { environment } from '../../environments/environment';

export class Constants {
    static DOMAIN_URL = environment.domain;
    static LOGIN_URL = Constants.DOMAIN_URL + 'api/login';
    static GET_EVENTS_URL = 'http://localhost:8002/events';
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
