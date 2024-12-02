// adminDepartmentList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingDepartmentID = null;
const BASE_URL = 'http://8.134.189.141:8080';

document.addEventListener('DOMContentLoaded', function() {
    loadDepartmentList(currentPage, pageSize);  
});

// 加载部门列表
function loadDepartmentList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/department/getDepartmentList?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayDepartments(data.data.records);
        } else {
            alert('获取部门列表失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示部门列表
function displayDepartments(departments) {
    const departmentListTbody = document.getElementById('department-list');
    departmentListTbody.innerHTML = '';

    if (departments.length === 0) {
        departmentListTbody.innerHTML = '<tr><td colspan="3">No departments found.</td></tr>';
        return;
    }

    departments.forEach(department => {
        const departmentRow = document.createElement('tr');
        departmentRow.innerHTML = `
            <td>${department.departmentID}</td>
            <td>${department.departmentName}</td>
            <td>
                <button onclick="openEditModal('${department.departmentID}', '${department.departmentName}')">Edit</button>
                <button onclick="deleteDepartment('${department.departmentID}')">Delete</button>
            </td>
        `;
        departmentListTbody.appendChild(departmentRow);
    });
}

// 打开添加模态窗口
function openAddModal() {
    currentEditingDepartmentID = null;
    document.getElementById('modal-title').textContent = 'Add Department';
    document.getElementById('departmentID-container').style.display = 'none'; // 隐藏 Department ID
    document.getElementById('edit-departmentName').value = '';
    openModal();
}

// 打开编辑模态窗口
function openEditModal(departmentID, departmentName) {
    currentEditingDepartmentID = departmentID;
    document.getElementById('modal-title').textContent = 'Edit Department';
    document.getElementById('departmentID-container').style.display = 'block'; // 显示 Department ID
    document.getElementById('edit-departmentID').value = departmentID;
    document.getElementById('edit-departmentName').value = departmentName;
    openModal();
}

function openModal() {
    document.getElementById('edit-modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('edit-modal').style.display = 'none';
}

// 保存部门信息更新或添加
function saveDepartmentUpdate() {
    const token = localStorage.getItem('token');
    const departmentName = document.getElementById('edit-departmentName').value;

    let apiUrl;
    let requestData;

    if (currentEditingDepartmentID) {
        // 更新部门
        apiUrl = `${BASE_URL}/department/updateDepartment`;
        requestData = {
            departmentID: currentEditingDepartmentID,
            departmentName: departmentName
        };
    } else {
        // 添加新部门
        apiUrl = `${BASE_URL}/department/insertDepartment`;
        requestData = {
            departmentName: departmentName
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
            alert(currentEditingDepartmentID ? '更新成功' : '添加成功');
            closeModal();
            loadDepartmentList(currentPage, pageSize);
        } else {
            alert('保存失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除部门
function deleteDepartment(departmentID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/department/deleteDepartment?departmentID=${departmentID}`, {
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
            loadDepartmentList(currentPage, pageSize);
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
        loadDepartmentList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadDepartmentList(currentPage, pageSize);
}
