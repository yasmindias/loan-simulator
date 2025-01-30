import http from 'k6/http';
import { check } from 'k6';

const NUMBER_OF_SIMULATIONS = 10000;

export default function () {
    const url = 'http://host.docker.internal:8080/simulation';
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    
    let payload = generate(NUMBER_OF_SIMULATIONS)
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

function generate(total) {
    let simulations = [];
    for (let i = 0; i < total; i++) {
        simulations.push({
            "totalValue": getRandomInt(100, 100000),
            "birthDate": `${getRandomInt(1925, 2007)}-01-27`,
            "paymentTerm": getRandomInt(2, 100)
        })
    }
    return simulations
}