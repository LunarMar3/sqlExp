// departmentList.js
const BASE_URL = 'http://8.134.189.141:8080';
let currentPage = 1;
const pageSize = 5;

document.addEventListener('DOMContentLoaded', function() {
    loadDepartmentList(currentPage, pageSize);  // 默认加载第一页，每页5个
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
        departmentListTbody.innerHTML = '<tr><td colspan="2">No departments found.</td></tr>';
        return;
    }

    departments.forEach(department => {
        const departmentRow = document.createElement('tr');
        departmentRow.innerHTML = `
            <td>${department.departmentID}</td>
            <td>${department.departmentName}</td>
        `;
        departmentListTbody.appendChild(departmentRow);
    });
}

// 分页功能 - 上一页
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        loadDepartmentList(currentPage, pageSize);
    }
}

// 分页功能 - 下一页
function nextPage() {
    currentPage++;
    loadDepartmentList(currentPage, pageSize);
}
