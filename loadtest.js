import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '1m', target: 10000 }, // Ramp up to 500 VUs in 1 minute
        { duration: '2m', target: 10000 }, // Stay at 500 VUs for 3 minutes
        { duration: '1m', target: 0 },   // Ramp down
    ],
    rps: 10000, // Limit to 500 requests per second
};

export default function () {
    const res = http.get('http://cwaloadbalancer-753397285.ap-south-1.elb.amazonaws.com:8081/adminredirect');
    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}