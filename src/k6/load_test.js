import http from 'k6/http';
import { check } from 'k6';

export let options = {
    discardResponseBodies: true,
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 10000, // requests
            timeUnit: '1s', // per second
            duration: '6s', // total duration of 6 seconds
            preAllocatedVUs: 2000, // number of VUs to pre-allocate
            maxVUs: 8000, // maximum number of VUs
        },
    },
    thresholds: {
        'http_req_duration': ['p(95)<100'], // 95% of requests must complete below 100ms
    },
};

export default function () {
    const url = 'http://host.docker.internal:8080/simulation';
    const payload = {
        "totalValue": 10000,
        "birthDate": `${getRandomInt(1925, 2007)}-01-27`,
        "paymentTerm": 36
    };
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    
    let res =  http.post(url, JSON.stringify(payload), params);
    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}