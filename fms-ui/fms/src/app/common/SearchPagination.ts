import { Pagination } from './Pagination';

export class SearchPagination<T>{
    page: number;
    size: number;
    sort: string;
    searchCriteria: T;
    isGlobalSearch: boolean;
}