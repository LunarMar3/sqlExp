// adminPatientList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingPatientID = null;
const BASE_URL = 'http://8.134.189.141:8080';
document.addEventListener('DOMContentLoaded', function() {
    loadPatientList(currentPage, pageSize);  // 默认加载第一页，每页5个
});

// 加载病人列表
function loadPatientList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/patient/getPatientList?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayPatients(data.data.records);
        } else {
            alert('获取病人列表失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 搜索病人
function searchPatients() {
    const token = localStorage.getItem('token');
    const keyword = document.getElementById('search-keyword').value;

    fetch(`${BASE_URL}/patient/searchPatients?page=1&size=${pageSize}&keyword=${keyword}`, {
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
            displayPatients(data.data.records);
        } else {
            alert('搜索病人失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示病人列表
function displayPatients(patients) {
    const patientListTbody = document.getElementById('patient-list');
    patientListTbody.innerHTML = '';

    if (patients.length === 0) {
        patientListTbody.innerHTML = '<tr><td colspan="9">No patients found.</td></tr>';
        return;
    }

    patients.forEach(patient => {
        const truncatedMedicalHistory = patient.medicalHistory 
            ? (patient.medicalHistory.length > 50 ? patient.medicalHistory.substring(0, 50) + '...' : patient.medicalHistory)
            : 'N/A';

        const patientRow = document.createElement('tr');
        patientRow.innerHTML = `
            <td>${patient.patientID}</td>
            <td>${patient.name}</td>
            <td>${patient.address || 'N/A'}</td>
            <td>${patient.contactNumber || 'N/A'}</td>
            <td>${patient.gender || 'N/A'}</td>
            <td>${patient.roomID || 'N/A'}</td>
            <td title="${patient.medicalHistory || 'N/A'}">${truncatedMedicalHistory}</td>
            <td>${patient.dateOfBirth || 'N/A'}</td>
            <td>
                <button onclick="openEditModal('${patient.patientID}', '${patient.name}', '${patient.address}', '${patient.contactNumber}', '${patient.gender}', '${patient.roomID}', '${patient.medicalHistory}', '${patient.dateOfBirth}')">Edit</button>
                <button onclick="deletePatient('${patient.patientID}')">Delete</button>
            </td>
        `;
        patientListTbody.appendChild(patientRow);
    });
}

// 打开编辑模态窗口
function openEditModal(patientID, name, address, contactNumber, gender, roomID, medicalHistory, dateOfBirth) {
    currentEditingPatientID = patientID;
    document.getElementById('edit-patientID').value = patientID;
    document.getElementById('edit-name').value = name;
    document.getElementById('edit-address').value = address;
    document.getElementById('edit-contactNumber').value = contactNumber;
    document.getElementById('edit-gender').value = gender;
    document.getElementById('edit-roomID').value = roomID;
    document.getElementById('edit-medicalHistory').value = medicalHistory;
    document.getElementById('edit-dateOfBirth').value = dateOfBirth;

    const modal = document.getElementById('edit-modal');
    modal.style.display = 'block';
}

// 关闭模态窗口
function closeModal() {
    const modal = document.getElementById('edit-modal');
    modal.style.display = 'none';
}

// 保存病人信息更新
function savePatientUpdate() {
    const token = localStorage.getItem('token');

    const updateData = {
        patientID: currentEditingPatientID,
        name: document.getElementById('edit-name').value,
        address: document.getElementById('edit-address').value,
        contactNumber: document.getElementById('edit-contactNumber').value,
        gender: document.getElementById('edit-gender').value,
        roomID: document.getElementById('edit-roomID').value,
        medicalHistory: document.getElementById('edit-medicalHistory').value,
        dateOfBirth: document.getElementById('edit-dateOfBirth').value,
    };

    fetch(`${BASE_URL}/patient/updatePatient`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        },
        body: JSON.stringify(updateData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            alert('更新成功');
            closeModal();
            loadPatientList(currentPage, pageSize);
        } else {
            alert('更新失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除病人
function deletePatient(patientID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/patient/deletePatient?patientID=${patientID}`, {
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
            loadPatientList(currentPage, pageSize);
        } else {
            alert('删除失败: ' + data.message);
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
        loadPatientList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadPatientList(currentPage, pageSize);
}
