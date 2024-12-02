// patientTreatmentList.js
const BASE_URL = 'http://8.134.189.141:8080';
let currentPage = 1;
const pageSize = 5;

document.addEventListener('DOMContentLoaded', function() {
    loadTreatmentList(currentPage, pageSize);  // 默认加载第一页，每页5个
});

// 加载诊疗记录列表
function loadTreatmentList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/treatment/getTreatment?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayTreatments(data.data.records);
        } else {
            alert('获取诊疗记录失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示诊疗记录
function displayTreatments(treatments) {
    const treatmentListTbody = document.getElementById('treatment-list');
    treatmentListTbody.innerHTML = '';

    if (treatments.length === 0) {
        treatmentListTbody.innerHTML = '<tr><td colspan="4">No treatment records found.</td></tr>';
        return;
    }

    treatments.forEach(treatment => {
        const treatmentRow = document.createElement('tr');
        treatmentRow.innerHTML = `
            <td>${treatment.treatmentID}</td>
            <td>${treatment.treatmentDate}</td>
            <td>${treatment.treatmentDetails}</td>
            <td>${treatment.prescription}</td>
        `;
        treatmentListTbody.appendChild(treatmentRow);
    });
}

// 分页功能 - 上一页
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        loadTreatmentList(currentPage, pageSize);
    }
}

// 分页功能 - 下一页
function nextPage() {
    currentPage++;
    loadTreatmentList(currentPage, pageSize);
}
