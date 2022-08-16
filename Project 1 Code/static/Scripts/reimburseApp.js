const baseUrl = "http://localhost:8080";

let employee;

async function login() {
    let newEmail = document.getElementById('emailInput').value;
    let empById = {
        email: newEmail
    }

    let empByIdJson = JSON.stringify(empById);

    let res = await fetch(`${baseUrl}/login?email=${newEmail}`);

    if(res.status === 200) {
        let data = await res.json();
        employee = data;
    }

    var empId = employee.eid;
    sessionStorage.setItem("empId", empId);

    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/dashboard.html');

}

async function getEmpById() {
    var empId = sessionStorage.getItem("empId");

    let res = await fetch(`${baseUrl}/login/${empId}`);

    if(res.status === 200) {
        let data = await res.json();
        employee = data;
    }
    console.log(employee);
    return employee;
}

async function getEmpById2(number) {
    let res = await fetch(`${baseUrl}/login/${number}`);

    if(res.status === 200) {
        let data = await res.json();
        employee = data;
    }
    console.log(employee);
    return employee;
}

function getEmpSession() {
    var empId = sessionStorage.getItem("empId");
    getEmpById();
    console.log(empId);
}

async function getBanner() {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    console.log(employee.namefl);
    
    var sPath = window.location.pathname;
    var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
    if(sPage == "dashboard.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`${employee.namefl}'s Dashboard`);
        dataDiv.append(header);
        if(employee.jobtitle != "Cashier" && employee.jobtitle != "Stocker" 
        && employee.jobtitle != "Human Resources") {
            let dataDiv = document.getElementById('awaitingApproval');
            let header = document.createElement('p');
            header.innerHTML = ("<label>Requests Needing Approval: </label>" +
                                "<a href='./approval.html'>here</a>");
            dataDiv.append(header);
        }
    } else if(sPage  == "emprequests.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`${employee.namefl}'s Requests`);
        dataDiv.append(header);
    } else if(sPage == "addrequest.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`Please input request information:`);
        dataDiv.append(header);
    } else if(sPage == "responserequests.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`Click on a request to address it:`);
        dataDiv.append(header);
    } else if(sPage == "response.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`Please respond to the following question:`);
        dataDiv.append(header);
    } else if(sPage == "approval.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = (`${employee.namefl}'s Approval Requests`);
        dataDiv.append(header);
    } else if(sPage == "moreinfo.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = ("Requesting More Information");
        dataDiv.append(header);
    } else if(sPage == "gradesubmission.html") {
        let dataDiv = document.getElementById('headerDiv');
        let header = document.createElement('h2');
        header.innerHTML = ("Submit a Grade");
        dataDiv.append(header);
    }
}

async function requestsPage() {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    var empId = employee.eid;
    sessionStorage.setItem("empId", empId);

    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/emprequests.html');
}

function dashboard() {
    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/dashboard.html');
}

async function displayRequests() {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    let res = await fetch(`${baseUrl}/login/${empId}/getrequests`);
    var data = [];
    var pend = [];
    var app = [];
    var rej = [];
    if(res.status === 200) {
        data = await res.json();
    }
    for(i = 0; i <= data.length - 1; i++) {
        if(data[i].finalDecision === "Pending") {
            pend.push(data[i]);
            console.log(pend);
        } else if(data[i].finalDecision === "Approved") {
            app.push(data[i]);
            console.log(app);
        } else {
            rej.push(data[i]);
            console.log(rej);
        }
    }


    if(pend.length > 0) {
        let reName = document.getElementById('requestName');
        let head = document.createElement('h4');
        head.innerHTML = ("Pending Requests:");
        reName.append(head);        
        for(i = 0; i <= pend.length - 1; i++) {
            let dataDiv = document.getElementById('requests');
            let par = document.createElement('p');
            let date = timeConverter(pend[i].datetime);
                par.innerHTML = ("<br>Start date of course: " + date +
                                "<br>Institution of course: " + pend[i].local +
                                "<br>Name of course:        " + pend[i].course +
                                "<br>Price of course: $     " + pend[i].price +
                                "<br>Grading format:        " + pend[i].gradeForm +
                                "<br>Event type:            " + pend[i].eventType +
                                "<br>Justification:         " + pend[i].justif +
                                "<br>Status of application: " + pend[i].status +
                                "<br>Final decision:        " + pend[i].finalDecision
                                );
                dataDiv.append(par);
        }
    } else {
        let reName = document.getElementById('requestName');
        let head = document.createElement('h4');
        head.innerHTML = ("Pending Requests:");
        reName.append(head);
        let dataDiv = document.getElementById('requests');
        let par = document.createElement('p');
        par.innerHTML = ("You do not have any pending requests.");
        dataDiv.append(par);
        }        
    

    if(app.length > 0) {
        let reName2 = document.getElementById('requestName2');
        let head2 = document.createElement('h4');
        head2.innerHTML = ("Approved Requests:");
        reName2.append(head2);
        for(i = 0; i <= app.length - 1; i++) {
            let dataDiv = document.getElementById('requests2');
            let par = document.createElement('p');
            let date = timeConverter(app[i].datetime);
            par.innerHTML = ("<br>Start date of course: " + date +
                            "<br>Institution of course: " + app[i].local +
                            "<br>Name of course:        " + app[i].course +
                            "<br>Price of course: $     " + app[i].price +
                            "<br>Grading format:        " + app[i].gradeForm +
                            "<br>Event type:            " + app[i].eventType +
                            "<br>Justification:         " + app[i].justif +
                            "<br>Status of application: " + app[i].status +
                            "<br>Final decision:        " + app[i].finalDecision
                            );
            dataDiv.append(par);
        }
    } else {
        let reName2 = document.getElementById('requestName2');
        let head2 = document.createElement('h4');
        head2.innerHTML = ("Approved Requests:");
        reName2.append(head2);
        let dataDiv = document.getElementById('requests2');
        let par = document.createElement('p');
        par.innerHTML = ("You do not have any approved requests.");
        dataDiv.append(par);
    }

    if(rej.length > 0) {
        let reName = document.getElementById('requestName3');
        let head = document.createElement('h4');
        head.innerHTML = ("Rejected Requests:");
        reName.append(head);
        for(i = 0; i <= rej.length - 1; i++) {
            let dataDiv = document.getElementById('requests3');
            let par = document.createElement('p');
            let date = timeConverter(rej[i].datetime);
            par.innerHTML = ("<br>Start date of course: " + date +
                            "<br>Institution of course: " + rej[i].local +
                            "<br>Name of course:        " + rej[i].course +
                            "<br>Price of course: $     " + rej[i].price +
                            "<br>Grading format:        " + rej[i].gradeForm +
                            "<br>Event type:            " + rej[i].eventType +
                            "<br>Justification:         " + rej[i].justif +
                            "<br>Status of application: " + rej[i].status +
                            "<br>Final decision:        " + rej[i].finalDecision
                            );
            dataDiv.append(par);
        }
    } else {
        let reName = document.getElementById('requestName3');
        let head = document.createElement('h4');
        head.innerHTML = ("Rejected Requests:");
        reName.append(head);
        let dataDiv = document.getElementById('requests3');
        let par = document.createElement('p');
        par.innerHTML = ("You do not have any approved requests.");
        dataDiv.append(par);
    }
}

    async function addRequest() {
        var empId = sessionStorage.getItem("empId");
        let dt = document.getElementById('dateTime').value;
        let date = new Date(dt);
        let datetime2 = Math.floor(date.getTime() / 1000);
        let local2 = document.getElementById('inst').value;
        let course2 = document.getElementById('course').value;
        let price2 = document.getElementById('price').value;
        let gradeForm2 = document.getElementById('gradeForm').value;
        let eventType2 = document.getElementById('eventType').value;
        let justif2 = document.getElementById('justif').value;
        let missedHours2 = document.getElementById('hours').value;
        let request = {
            datetime: datetime2,
            local: local2,
            course: course2,
            price: price2,
            gradeForm: gradeForm2,
            eventType: eventType2,
            justif: justif2,
            missedHours: missedHours2
        }
        if(empId != 0) {
            var xhr = new XMLHttpRequest();
            var url = (`${baseUrl}/login/${empId}/dashboard/request`);
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function() { };
            var data = JSON.stringify(request);
            console.log(data);
            xhr.send(data);
            await delay(1000);
            dashboard();
        } else {
            let fields = document.getElementById('blankFields');
            let phar = document.createElement('p');
            phar.innerHTML = ("You must fill in all fields to continue.");
            fields.append(phar);
        }
    }

    async function displayResponseRequests() {
        var empId = sessionStorage.getItem("empId");
        let employee = await getEmpById();
        let res = await fetch(`${baseUrl}/login/${empId}/getrequests`);
        var data = [];
        var responseReq = [];
        if(res.status === 200) {
            data = await res.json();
        }
        for(i = 0; i <= data.length - 1; i++) {
            if(data[i].status === "Awaiting Response") {
                responseReq.push(data[i]);
            }
        }
        if(responseReq.length > 0) {
            if(responseReq != null) {
                    for(i = 0; i <= responseReq.length - 1; i++) {
                        let dataDiv = document.getElementById('responseReq');
                        let par = document.createElement('p');
                        par.innerHTML = ("<br>You have a response request for the course '" + responseReq[i].course +
                                        "' at the requested price reimbursement of $" + responseReq[i].price +
                                        ". If you would like to respond to this request click here:" +
                                        "<br><button type='button' onclick='getRequest" + [i] + "(); myFunction()'>Click Here</button>"
                                        );
                        dataDiv.append(par);
                        console.log("Is this here?")
                    }
                }
            } else {
                let dataDiv = document.getElementById('responseReq');
                let par = document.createElement('p');
                par.innerHTML = ("You do not have any response requests." +
                                "<br><button onclick='dashboard()'>Go Back</button>"
                                );
                dataDiv.append(par);
                console.log(par);
            }
    }

    async function getAwaiting() {
        var empId = sessionStorage.getItem("empId");
        let employee = await getEmpById();
        let res = await fetch(`${baseUrl}/login/${empId}/getrequests`);
        console.log(res);
        var data = [];
        var responseReq = [];
        if(res.status === 200) {
            data = await res.json();
        }
        for(i = 0; i <= data.length - 1; i++) {
            if(data[i].status === "Awaiting Response") {
                responseReq.push(data[i]);
            }
        }
        return responseReq;
    }
    
    async function getRequest0() {
        responseReq = await getAwaiting();
        console.log(responseReq)
        if(responseReq != null) {
            let dataDiv = document.getElementById('responseNext');
            let par = document.createElement('p');
            par.innerHTML = ("<b>Please answer the following question:</b> " +
                            "<br>" + responseReq[0].justif +
                            "<br><textarea rows='4' cols='50' id='textBox'></textarea>" +
                            "<br><button type='button' onclick='answerReq()'>Submit Response</button>"
                        );
            dataDiv.append(par);
            let reqnum = 0;
            sessionStorage.setItem("reqnum", reqnum);
        } else {
            console.log("There doesn't seem to be anything here.");
        }
    }

    async function getRequest1() {
        responseReq = await getAwaiting();
        console.log(responseReq)
        if(responseReq != null) {
            let dataDiv = document.getElementById('responseNext');
            let par = document.createElement('p');
            par.innerHTML = ("<b>Please answer the following question:</b> " +
                            "<br>" + responseReq[1].justif +
                            "<br><textarea rows='4' cols='50' id='textBox'></textarea>" +
                            "<br><button type='button' onclick='answerReq()'>Submit Response</button>"
                        );
            dataDiv.append(par);
            let reqnum = 1;
            sessionStorage.setItem("reqnum", reqnum);
        } else {
            console.log("There doesn't seem to be anything here.");
        }
    }

    async function getRequest3() {
        responseReq = await getAwaiting();
        console.log(responseReq)
        if(responseReq != null) {
            let dataDiv = document.getElementById('responseNext');
            let par = document.createElement('p');
            par.innerHTML = ("<b>Please answer the following question:</b> " +
                            "<br>" + responseReq[3].justif +
                            "<br><textarea rows='4' cols='50' id='textBox'></textarea>" +
                            "<br><button type='button' onclick='answerReq()'>Submit Response</button>"
                        );
            dataDiv.append(par);
            let reqnum = 3;
            sessionStorage.setItem("reqnum", reqnum);
        } else {
            console.log("There doesn't seem to be anything here.");
        }
    }

    async function getRequest4() {
        responseReq = await getAwaiting();
        console.log(responseReq)
        if(responseReq != null) {
            let dataDiv = document.getElementById('responseNext');
            let par = document.createElement('p');
            par.innerHTML = ("<b>Please answer the following question:</b> " +
                            "<br>" + responseReq[4].justif + "0" +
                            "<br><textarea rows='4' cols='50' id='textBox'></textarea>" +
                            "<br><button type='button' onclick='answerReq()'>Submit Response</button>"
                        );
            dataDiv.append(par);
            let reqnum = 4;
            sessionStorage.setItem("reqnum", reqnum);
        } else {
            console.log("There doesn't seem to be anything here.");
        }
    }

    async function answerReq() {
        var empId = sessionStorage.getItem("empId");
        var reqnum = sessionStorage.getItem("reqnum");
        let rNum = responseReq[reqnum].rNum
        let datetime2 = responseReq[reqnum].datetime;
        let local2 = responseReq[reqnum].local;
        let course2 = responseReq[reqnum].course;
        let price2 = responseReq[reqnum].price;
        let gradeForm2 = responseReq[reqnum].gradeForm;
        let eventType2 = responseReq[reqnum].eventType;
        let justif2 = document.getElementById('textBox').value;
        let status2 = "Awaiting Coordinator";
        let missedHours2 = responseReq[reqnum].missedHours;
        let request = {
            rNum: rNum,
            datetime: datetime2,
            local: local2,
            course: course2,
            price: price2,
            gradeForm: gradeForm2,
            eventType: eventType2,
            justif: justif2,
            status: status2,
            missedHours: missedHours2
        }
        var xhr = new XMLHttpRequest();
        var url = (`${baseUrl}/login/${empId}/dashboard/respond`);
        xhr.open("PATCH", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() { };
        var data = JSON.stringify(request);
        xhr.send(data);
        myFunction2();
        let dataDiv = document.getElementById('submitted');
        let par = document.createElement('p');
        par.innerHTML = ("Your response has been recorded." +
                        "<br><button type='button' onclick='dashboard()'>Go Back</button>"
                        );
        dataDiv.append(par);

}

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year;
    return time;
  }

  async function getApprove() {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    let res = await fetch(`${baseUrl}/login/${empId}/dashboard/getawaiting`);
    var data = [];
    var urge = [];
    var norm = [];
    if(res.status === 200) {
        data = await res.json();
    }
    for(i = 0; i <= data.length - 1; i++) {
        let date = Date.now() / 1000
        console.log(timeConverter(date))
        if((date) - data[i].datetime < 604800) {
            urge.push(data[i]);
        } else {
            norm.push(data[i]);
        }
    console.log(urge.length);
    console.log(norm.length);
    }
    if(urge.length > 0) {
        let dataDiv = document.getElementById('requestUrgent');
        let header = document.createElement('h2');
        header.innerHTML = ("Urgent Requests:");
        dataDiv.append(header);
        for(i = 0; i <= urge.length - 1; i++) {
            let employee = await getEmpById2(urge[i].eid);
            let dataDiv2 = document.getElementById('requestsUrgent');
            let par = document.createElement('p');
            let date = timeConverter(urge[i].datetime);
            par.innerHTML = ("<br>Employee Name:          " + employee.namefl +
                            "<br>Start date of course:    " + date +
                            "<br>Institution of course:   " + urge[i].local +
                            "<br>Name of course:          " + urge[i].course +
                            "<br>Price of course:         " + urge[i].price +
                            "<br>Grading format:          " + urge[i].gradeForm +
                            "<br>Event type:              " + urge[i].eventType +
                            "<br>Justification:           " + urge[i].justif +
                            "<br><button type='button' onclick='approve(" + urge[i].rNum + ")'>Approve Request</button>" +
                            "<button type='button' onclick='deny(" + urge[i].rNum + ")'>Deny Request</button>" +
                            "<button type='button' onclick='moreInfo(" + urge[i].rNum + ")'>Inquire About Request</button>"
                            );
            dataDiv2.append(par);
        }        
    } else {
        let dataDiv = document.getElementById('requestNormal');
        let header = document.createElement('h2');
        header.innerHTML = ("No Urgent Requests");
        dataDiv.append(header);
    } 

    if(norm.length > 0) {
        let dataDiv = document.getElementById('requestNormal');
        let header = document.createElement('h2');
        header.innerHTML = ("Normal Requests:");
        dataDiv.append(header);
        for(i = 0; i <= norm.length - 1; i++) {
            let employee = await getEmpById2(norm[i].eid);
            dataDiv.append(header);
            let dataDiv2 = document.getElementById('requestsNormal');
            let par = document.createElement('p');
            let date = timeConverter(norm[i].datetime);
            par.innerHTML = ("<br>Employee Name:          " + employee.namefl +
                            "<br>Start date of course:    " + date +
                            "<br>Institution of course:   " + norm[i].local +
                            "<br>Name of course:          " + norm[i].course +
                            "<br>Price of course:         " + norm[i].price +
                            "<br>Grading format:          " + norm[i].gradeForm +
                            "<br>Event type:              " + norm[i].eventType +
                            "<br>Justification:           " + norm[i].justif +
                            "<br><button type='button' onclick='approve(" + norm[i].rNum + ")'>Approve Request</button>" +
                            "<button type='button' onclick='deny(" + norm[i].rNum + ")'>Deny Request</button>" +
                            "<button type='button' onclick='moreInfo(" + norm[i].rNum + ")'>Inquire About Request</button>"
                            );
            dataDiv2.append(par);
            }
    } else {
        let dataDiv = document.getElementById('requestNormal');
        let header = document.createElement('h2');
        header.innerHTML = ("No Normal Requests");
        dataDiv.append(header);
    }

}

async function approve(rnum) {
    console.log(rnum);
    var empId = sessionStorage.getItem("empId");
    var xhr = new XMLHttpRequest();
    var url = (`${baseUrl}/login/${empId}/dashboard/approve`);
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() { };
    let ra = {
        "rNum": rnum
    }
    var data = JSON.stringify(ra);
    xhr.send(data);
    await delay(1000);
    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/dashboard.html');
}

async function deny(rnum) {
    console.log(rnum);
    var empId = sessionStorage.getItem("empId");
    var xhr = new XMLHttpRequest();
    var url = (`${baseUrl}/login/${empId}/dashboard/deny`);
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() { };
    let ra = {
        "rNum": rnum
    }
    var data = JSON.stringify(ra);
    xhr.send(data);
    await delay(1000);
    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/dashboard.html');
}

function reload() {
    location.reload();
}

function delay(time) {
    return new Promise(resolve => setTimeout(resolve, time));
}

async function moreInfo(rnum) {
    sessionStorage.setItem("rnum", rnum);
    await delay(500)
    location.assign('file:///C:/Users/cunic/OneDrive/Desktop/Revature%20Class%20Items/Project%201/Project%201%20Code/static/HTML/moreinfo.html');
}

async function answerReq2() {
    var empId = sessionStorage.getItem("empId");
    var reqnum = sessionStorage.getItem("rnum");
    let justif2 = document.getElementById('textBox').value;
    console.log(reqnum);
    let request2 = {
        "rNum": reqnum,
        "justif": justif2
    }
    console.log(request2);
    var xhr = new XMLHttpRequest();
    var url = (`${baseUrl}/login/${empId}/dashboard/moreinfo`);
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() { };
    var data = JSON.stringify(request2);
    xhr.send(data);
    //myFunction2();
    let dataDiv = document.getElementById('submitted');
    let par = document.createElement('p');
    par.innerHTML = ("Your response has been recorded." +
                    "<br><button type='button' onclick='dashboard()'>Go Back</button>"
                    );
    dataDiv.append(par);

}

async function displayGrades() {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    let res = await fetch(`${baseUrl}/login/${empId}/getrequests`);
    var data = [];
    var grades = [];
    if(res.status === 200) {
        data = await res.json();
    }
    for(i = 0; i <= data.length - 1; i++) {
        if(data[i].status === "Awaiting Grade") {
            grades.push(data[i]);
            console.log(grades);
        }
    }
    if(grades.length > 0) {
        let reName = document.getElementById('gradeName');
        let head = document.createElement('h4');
        head.innerHTML = ("Awaiting Grades:");
        reName.append(head);        
        for(i = 0; i <= grades.length - 1; i++) {
            if(grades[i].gradeForm == "A-C") {
                let dataDiv = document.getElementById('grades');
                let par = document.createElement('p');
                let date = timeConverter(grades[i].datetime);
                    par.innerHTML = ("<br>Start date of course: " + date +
                                    "<br>Institution of course: " + grades[i].local +
                                    "<br>Name of course:        " + grades[i].course +
                                    "<br>Grading format:        " + grades[i].gradeForm +
                                    "<br>Event type:            " + grades[i].eventType +
                                    "<br>Status of application: " + grades[i].status +
                                    "<br><label for='finalAC'>Final Grade:</label>" +
                                    "<select id='finalAC' name='finalAC' id='FinalAC'>" +
                                        "<option value='A'>A</option>" +
                                        "<option value='B'>B</option>" +
                                        "<option value='C'>C</option>" +
                                    "</select><br></br>" +
                                    "<button type='button' onclick='submitGrade(" + [i] + ")'>Submit Grade</button>"
                                    );
                    dataDiv.append(par);
            } else if(grades[i].gradeForm == "P/F") {
                let dataDiv = document.getElementById('grades');
                let par = document.createElement('p');
                let date = timeConverter(grades[i].datetime);
                    par.innerHTML = ("<br>Start date of course: " + date +
                                    "<br>Institution of course: " + grades[i].local +
                                    "<br>Name of course:        " + grades[i].course +
                                    "<br>Grading format:        " + grades[i].gradeForm +
                                    "<br>Event type:            " + grades[i].eventType +
                                    "<br>Status of application: " + grades[i].status +
                                    "<br><label for='finalPF'>Final Grade:</label>" +
                                    "<select id='finalPF' name='finalPF' id='FinalPF'>" +
                                        "<option value='P'>P</option>" +
                                        "<option value='F'>F</option>" +
                                    "</select><br></br>" +
                                    "<button type='button' onclick='submitGrade(" + [i] + ")'>Submit Grade</button>"
                                    );
                    dataDiv.append(par);

            } else if(grades[i].gradeForm == "Complete/Incomplete") {
                let dataDiv = document.getElementById('grades');
                let par = document.createElement('p');
                let date = timeConverter(grades[i].datetime);
                    par.innerHTML = ("<br>Start date of course: " + date +
                                    "<br>Institution of course: " + grades[i].local +
                                    "<br>Name of course:        " + grades[i].course +
                                    "<br>Grading format:        " + grades[i].gradeForm +
                                    "<br>Event type:            " + grades[i].eventType +
                                    "<br>Status of application: " + grades[i].status +
                                    "<br><label for='finalCI'>Final Grade:</label>" +
                                    "<select id='finalCI' name='finalCI' id='FinalCI'>" +
                                        "<option value='Complete'>Complete</option>" +
                                        "<option value='Incomplete'>Incomplete</option>" +
                                    "</select><br></br>" +
                                    "<button type='button' onclick='submitGrade(" + [i] + ")'>Submit Grade</button>"
                                    );
                    dataDiv.append(par);

            }
        }
    } else {
        let dataDiv = document.getElementById('grades');
        let par = document.createElement('p');
        par.innerHTML = ("You do not have any pending grades." +
                        "<br><button type='button' onclick='dashboard()'>Go Back</button>");
        dataDiv.append(par);
    }  
}

async function submitGrade(num) {
    var empId = sessionStorage.getItem("empId");
    let employee = await getEmpById();
    let res = await fetch(`${baseUrl}/login/${empId}/getrequests`);
    var data = [];
    var grades = [];
    if(res.status === 200) {
        data = await res.json();
    }
    for(i = 0; i <= data.length - 1; i++) {
        if(data[i].status === "Awaiting Grade") {
            grades.push(data[i]);
            console.log(grades);
        }
    }
    let num2 = grades[num].rNum
    console.log(grades[num].gradeForm);
    if(grades[num].gradeForm == "A-C") {
        var grade = document.getElementById('finalAC').value;
        var empId = sessionStorage.getItem("empId");
        var xhr = new XMLHttpRequest();
        var url = (`${baseUrl}/login/${empId}/dashboard/postgrade?reimnum=${num2}&grade=${grade}`);
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() { };
        var data = JSON.stringify(grade);
        xhr.send(data);
        await delay(1000);
        dashboard();
    } else if(grades[num].gradeForm == "P/F") {
        var grade = document.getElementById('finalPF').value;
        var empId = sessionStorage.getItem("empId");
        var xhr = new XMLHttpRequest();
        var url = (`${baseUrl}/login/${empId}/dashboard/postgrade?reimnum=${num2}&grade=${grade}`);
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() { };
        var data = JSON.stringify(grade);
        xhr.send(data);
        await delay(1000);
        dashboard();
    } else if(grades[num].gradeForm == "Complete/Incomplete") {
        let num2 = grades[num].rNum        
        var grade = document.getElementById('finalCI').value;
        console.log(grade)
        var empId = sessionStorage.getItem("empId");
        var xhr = new XMLHttpRequest();
        var url = (`${baseUrl}/login/${empId}/dashboard/postgrade?reimnum=${num2}&grade=${grade}`);
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() { };
        var data = JSON.stringify(grade);
        xhr.send(data);
        await delay(1000);
        dashboard();
    }
}

async function getLeft() {
    var empId = sessionStorage.getItem("empId");

    let res = await fetch(`${baseUrl}/login/${empId}/dashboard/gettuition`);

    if(res.status === 200) {
        let data = await res.json();
        amount = data;
    }
    console.log(amount);
    let dataDiv = document.getElementById('left');
    let par = document.createElement('p');
    par.innerHTML = ("The amount of tuition left for the year is $" + amount + ".");
    dataDiv.append(par);
}





