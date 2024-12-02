// adminTreatmentList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingTreatmentID = null;
const BASE_URL = 'http://8.134.189.141:8080';
document.addEventListener('DOMContentLoaded', function() {
    loadTreatmentList(currentPage, pageSize);  // 默认加载第一页，每页5个
});

// 加载诊疗记录列表
function loadTreatmentList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/treatment/getTreatmentList?page=${page}&size=${size}`, {
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

// 显示诊疗记录列表
function displayTreatments(treatments) {
    const treatmentListTbody = document.getElementById('treatment-list');
    treatmentListTbody.innerHTML = '';

    if (treatments.length === 0) {
        treatmentListTbody.innerHTML = '<tr><td colspan="7">No treatments found.</td></tr>';
        return;
    }

    treatments.forEach(treatment => {
        const treatmentRow = document.createElement('tr');
        treatmentRow.innerHTML = `
            <td>${treatment.treatmentID}</td>
            <td>${treatment.patientID}</td>
            <td>${treatment.doctorID}</td>
            <td>${treatment.treatmentDate}</td>
            <td>${treatment.treatmentDetails}</td>
            <td>${treatment.prescription}</td>
            <td>
                <button onclick="openEditModal('${treatment.treatmentID}', '${treatment.patientID}', '${treatment.doctorID}', '${treatment.treatmentDate}', '${treatment.treatmentDetails}', '${treatment.prescription}')">Edit</button>
                <button onclick="deleteTreatment('${treatment.treatmentID}')">Delete</button>
            </td>
        `;
        treatmentListTbody.appendChild(treatmentRow);
    });
}

function openAddModal() {
    currentEditingTreatmentID = null;
    document.getElementById('modal-title').textContent = 'Add Treatment';
    document.getElementById('treatmentID-container').style.display = 'none';
    document.getElementById('edit-treatmentID').value = '';
    document.getElementById('edit-patientID').value = '';
    document.getElementById('edit-doctorID').value = '';
    document.getElementById('edit-treatmentDate').value = '';
    document.getElementById('edit-treatmentDetails').value = '';
    document.getElementById('edit-prescription').value = '';
    openModal();
}

// 打开编辑模态窗口
function openEditModal(treatmentID, patientID, doctorID, treatmentDate, treatmentDetails, prescription) {
    currentEditingTreatmentID = treatmentID;
    document.getElementById('modal-title').textContent = 'Edit Treatment';
    document.getElementById('treatmentID-container').style.display = 'block';
    document.getElementById('edit-treatmentID').value = treatmentID;
    document.getElementById('edit-patientID').value = patientID;
    document.getElementById('edit-doctorID').value = doctorID;
    document.getElementById('edit-treatmentDate').value = treatmentDate;
    document.getElementById('edit-treatmentDetails').value = treatmentDetails;
    document.getElementById('edit-prescription').value = prescription;
    openModal();
}

function openModal() {
    document.getElementById('edit-modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('edit-modal').style.display = 'none';
}

// 保存诊疗记录更新或添加
function saveTreatmentUpdate() {
    const token = localStorage.getItem('token');
    const patientID = document.getElementById('edit-patientID').value;
    const doctorID = document.getElementById('edit-doctorID').value;
    const treatmentDate = document.getElementById('edit-treatmentDate').value;
    const treatmentDetails = document.getElementById('edit-treatmentDetails').value;
    const prescription = document.getElementById('edit-prescription').value;

    let apiUrl;
    let requestData;

    if (currentEditingTreatmentID) {
        // 更新诊疗记录
        apiUrl = `${BASE_URL}/treatment/updateTreatment`;
        requestData = {
            treatmentID: currentEditingTreatmentID,
            patientID: patientID,
            doctorID: doctorID,
            treatmentDate: treatmentDate,
            treatmentDetails: treatmentDetails,
            prescription: prescription
        };
    } else {
        // 添加新诊疗记录
        apiUrl = `${BASE_URL}/treatment/insertTreatment`;
        requestData = {
            patientID: patientID,
            doctorID: doctorID,
            treatmentDate: treatmentDate,
            treatmentDetails: treatmentDetails,
            prescription: prescription
        };
    }

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert(currentEditingTreatmentID ? '更新成功' : '添加成功');
            closeModal();
            loadTreatmentList(currentPage, pageSize);
        } else {
            alert('保存失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除诊疗记录
function deleteTreatment(treatmentID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/treatment/deleteTreatment?treatmentID=${treatmentID}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('删除成功');
            loadTreatmentList(currentPage, pageSize);
        } else {
            alert('删除失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 搜索诊疗记录
function searchTreatments() {
    const token = localStorage.getItem('token');
    const keyword = document.getElementById('search-keyword').value;

    fetch(`${BASE_URL}/treatment/getTreatmentList?page=1&size=${pageSize}&keyword=${keyword}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            currentPage = 1;  // 搜索时从第一页开始
            displayTreatments(data.data.records);
        } else {
            alert('搜索诊疗记录失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 分页功能
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        loadTreatmentList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadTreatmentList(currentPage, pageSize);
}
