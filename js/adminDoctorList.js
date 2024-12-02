// adminDoctorList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingDoctorID = null;
const BASE_URL = 'http://8.134.189.141:8080';
document.addEventListener('DOMContentLoaded', function() {
    loadDoctorList(currentPage, pageSize); 
});

// 加载医生列表
function loadDoctorList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/doctor/getDoctorList?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayDoctors(data.data.records);
        } else {
            alert('获取医生列表失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 搜索医生
function searchDoctors() {
    const token = localStorage.getItem('token');
    const keyword = document.getElementById('search-keyword').value;

    fetch(`${BASE_URL}/doctor/searchDoctors?page=1&size=${pageSize}&keyword=${keyword}`, {
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
            displayDoctors(data.data.records);
        } else {
            alert('搜索医生失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示医生列表
function displayDoctors(doctors) {
    const doctorListTbody = document.getElementById('doctor-list');
    doctorListTbody.innerHTML = '';

    if (doctors.length === 0) {
        doctorListTbody.innerHTML = '<tr><td colspan="8">No doctors found.</td></tr>';
        return;
    }

    doctors.forEach(doctor => {
        const doctorRow = document.createElement('tr');
        doctorRow.innerHTML = `
            <td>${doctor.doctorID}</td>
            <td>${doctor.name}</td>
            <td>${doctor.gender || 'N/A'}</td>
            <td>${doctor.departmentID || 'N/A'}</td>
            <td>${doctor.contactNumber || 'N/A'}</td>
            <td>${doctor.email || 'N/A'}</td>
            <td>${doctor.verified === 1 ? 'Yes' : 'No'}</td>
            <td>
                <button onclick="openEditModal('${doctor.doctorID}', '${doctor.name}', '${doctor.gender}', '${doctor.departmentID}', '${doctor.contactNumber}', '${doctor.email}', '${doctor.verified}')">Edit</button>
                <button onclick="deleteDoctor('${doctor.doctorID}')">Delete</button>
            </td>
        `;
        doctorListTbody.appendChild(doctorRow);
    });
}

// 打开编辑模态窗口
function openEditModal(doctorID, name, gender, departmentID, contactNumber, email, verified) {
    currentEditingDoctorID = doctorID;
    document.getElementById('edit-doctorID').value = doctorID;
    document.getElementById('edit-name').value = name;
    document.getElementById('edit-gender').value = gender;
    document.getElementById('edit-departmentID').value = departmentID;
    document.getElementById('edit-contactNumber').value = contactNumber;
    document.getElementById('edit-email').value = email;
    document.getElementById('edit-verified').value = verified;

    const modal = document.getElementById('edit-modal');
    modal.style.display = 'block';
}

// 关闭模态窗口
function closeModal() {
    const modal = document.getElementById('edit-modal');
    modal.style.display = 'none';
}

// 保存医生信息更新
function saveDoctorUpdate() {
    const token = localStorage.getItem('token');

    const updateData = {
        doctorID: currentEditingDoctorID,
        name: document.getElementById('edit-name').value,
        gender: document.getElementById('edit-gender').value,
        departmentID: document.getElementById('edit-departmentID').value,
        contactNumber: document.getElementById('edit-contactNumber').value,
        email: document.getElementById('edit-email').value,
        verified: parseInt(document.getElementById('edit-verified').value)
    };

    fetch(`${BASE_URL}/doctor/updateDoctor`, {
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
            loadDoctorList(currentPage, pageSize);
        } else {
            alert('更新失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除医生
function deleteDoctor(doctorID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/doctor/deleteDoctor?doctorID=${doctorID}`, {
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
            loadDoctorList(currentPage, pageSize);
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
        loadDoctorList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadDoctorList(currentPage, pageSize);
}
