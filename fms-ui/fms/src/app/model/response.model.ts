export class Response<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    first: boolean;
    last: boolean;
    size: number;
}