function leftTrim(s) {
    let notSpaceIndex=0;
    let resp = '';
    for (let i = notSpaceIndex; i < s.length; i++) {
        if (s[i] !== ' ') {
            notSpaceIndex = i;
            break;
        }
    }
    for (let i = notSpaceIndex; i < s.length; i++) {
        resp += s[i];
    }
    return resp;
}

function rightTrim(s) {
    let notSpaceIndex = s.length - 1;
    let resp = '';
    for (let i = notSpaceIndex; i >= 0; i--) {
        if (s[i] !== ' ') {
            notSpaceIndex = i;
            break;
        }
    }
    for (let i = 0; i <= notSpaceIndex; i++) {
        resp += s[i];
    }
    return resp;
}

function allTrim(s) {
    let resp = rightTrim(leftTrim(s));
    return resp;
}

const s = '   tads    ';
console.log(rightTrim(s));
console.log(leftTrim(s));
console.log(allTrim(s));