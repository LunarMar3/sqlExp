// adminNurseList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingNurseID = null;
const BASE_URL = 'http://8.134.189.141:8080';

document.addEventListener('DOMContentLoaded', function() {
    loadNurseList(currentPage, pageSize);  // 默认加载第一页，每页5个
});

// 加载护士列表
function loadNurseList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/nurse/getNurseList?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayNurses(data.data.records);
        } else {
            alert('获取护士列表失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示护士列表
function displayNurses(nurses) {
    const nurseListTbody = document.getElementById('nurse-list');
    nurseListTbody.innerHTML = '';

    if (nurses.length === 0) {
        nurseListTbody.innerHTML = '<tr><td colspan="6">No nurses found.</td></tr>';
        return;
    }

    nurses.forEach(nurse => {
        const nurseRow = document.createElement('tr');
        nurseRow.innerHTML = `
            <td>${nurse.nurseID}</td>
            <td>${nurse.name}</td>
            <td>${nurse.gender}</td>
            <td>${nurse.contactNumber}</td>
            <td>${nurse.assignedRoomID}</td>
            <td>
                <button onclick="openEditModal('${nurse.nurseID}', '${nurse.name}', '${nurse.gender}', '${nurse.contactNumber}', '${nurse.assignedRoomID}')">Edit</button>
                <button onclick="deleteNurse('${nurse.nurseID}')">Delete</button>
            </td>
        `;
        nurseListTbody.appendChild(nurseRow);
    });
}

// 打开添加模态窗口
function openAddModal() {
    currentEditingNurseID = null;
    document.getElementById('modal-title').textContent = 'Add Nurse';
    document.getElementById('nurseID-container').style.display = 'none';
    document.getElementById('edit-nurseID').value = '';
    document.getElementById('edit-name').value = '';
    document.getElementById('edit-gender').value = '';
    document.getElementById('edit-contactNumber').value = '';
    document.getElementById('edit-assignedRoomID').value = '';
    openModal();
}

// 打开编辑模态窗口
function openEditModal(nurseID, name, gender, contactNumber, assignedRoomID) {
    currentEditingNurseID = nurseID;
    document.getElementById('modal-title').textContent = 'Edit Nurse';
    document.getElementById('nurseID-container').style.display = 'block';
    document.getElementById('edit-nurseID').value = nurseID;
    document.getElementById('edit-name').value = name;
    document.getElementById('edit-gender').value = gender;
    document.getElementById('edit-contactNumber').value = contactNumber;
    document.getElementById('edit-assignedRoomID').value = assignedRoomID;
    openModal();
}

function openModal() {
    document.getElementById('edit-modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('edit-modal').style.display = 'none';
}

// 保存护士信息更新或添加
function saveNurseUpdate() {
    const token = localStorage.getItem('token');
    const name = document.getElementById('edit-name').value;
    const gender = document.getElementById('edit-gender').value;
    const contactNumber = document.getElementById('edit-contactNumber').value;
    const assignedRoomID = document.getElementById('edit-assignedRoomID').value;

    let apiUrl;
    let requestData;

    if (currentEditingNurseID) {
        // 更新护士
        apiUrl = `${BASE_URL}/nurse/updateNurse`;
        requestData = {
            nurseID: currentEditingNurseID,
            name: name,
            gender: gender,
            contactNumber: contactNumber,
            assignedRoomID: assignedRoomID
        };
    } else {
        // 添加新护士
        apiUrl = `${BASE_URL}/nurse/insertNurse`;
        requestData = {
            name: name,
            gender: gender,
            contactNumber: contactNumber,
            assignedRoomID: assignedRoomID
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
            alert(currentEditingNurseID ? '更新成功' : '添加成功');
            closeModal();
            loadNurseList(currentPage, pageSize);
        } else {
            alert('保存失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除护士
function deleteNurse(nurseID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/nurse/deleteNurse?nurseID=${nurseID}`, {
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
            loadNurseList(currentPage, pageSize);
        } else {
            alert('删除失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 搜索护士
function searchNurses() {
    const token = localStorage.getItem('token');
    const keyword = document.getElementById('search-keyword').value;

    fetch(`${BASE_URL}/nurse/searchNurses?page=1&size=${pageSize}&keyword=${keyword}`, {
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
            displayNurses(data.data.records);
        } else {
            alert('搜索护士失败: ' + data.message);
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
        loadNurseList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadNurseList(currentPage, pageSize);
}
