export class User {
    userId: number;
    username: string;
    password: string;
    authorities: Authorities[];
}
export class Authorities {
    authority: string;
}
