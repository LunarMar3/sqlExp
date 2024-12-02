// adminRoomList.js

let currentPage = 1;
const pageSize = 5;
let currentEditingRoomID = null;
const BASE_URL = 'http://8.134.189.141:8080';

document.addEventListener('DOMContentLoaded', function() {
    loadRoomList(currentPage, pageSize);  // 默认加载第一页，每页5个
});

// 加载房间列表
function loadRoomList(page, size) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/room/getRoomList?page=${page}&size=${size}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayRooms(data.data.records);
        } else {
            alert('获取房间列表失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 显示房间列表
function displayRooms(rooms) {
    const roomListTbody = document.getElementById('room-list');
    roomListTbody.innerHTML = '';

    if (rooms.length === 0) {
        roomListTbody.innerHTML = '<tr><td colspan="6">No rooms found.</td></tr>';
        return;
    }

    rooms.forEach(room => {
        const roomRow = document.createElement('tr');
        roomRow.innerHTML = `
            <td>${room.roomID}</td>
            <td>${room.roomNumber}</td>
            <td>${room.roomType}</td>
            <td>${room.capacity}</td>
            <td>${room.occupiedBeds}</td>
            <td>
                <button onclick="openEditModal('${room.roomID}', '${room.roomNumber}', '${room.roomType}', '${room.capacity}', '${room.occupiedBeds}')">Edit</button>
                <button onclick="deleteRoom('${room.roomID}')">Delete</button>
            </td>
        `;
        roomListTbody.appendChild(roomRow);
    });
}

// 打开添加模态窗口
function openAddModal() {
    currentEditingRoomID = null;
    document.getElementById('modal-title').textContent = 'Add Room';
    document.getElementById('roomID-container').style.display = 'none';
    document.getElementById('edit-roomNumber').value = '';
    document.getElementById('edit-roomType').value = '';
    document.getElementById('edit-capacity').value = '';
    document.getElementById('edit-occupiedBeds').value = '';
    openModal();
}

// 打开编辑模态窗口
function openEditModal(roomID, roomNumber, roomType, capacity, occupiedBeds) {
    currentEditingRoomID = roomID;
    document.getElementById('modal-title').textContent = 'Edit Room';
    document.getElementById('roomID-container').style.display = 'block';
    document.getElementById('edit-roomID').value = roomID;
    document.getElementById('edit-roomNumber').value = roomNumber;
    document.getElementById('edit-roomType').value = roomType;
    document.getElementById('edit-capacity').value = capacity;
    document.getElementById('edit-occupiedBeds').value = occupiedBeds;
    openModal();
}

function openModal() {
    document.getElementById('edit-modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('edit-modal').style.display = 'none';
}

// 保存房间信息更新或添加
function saveRoomUpdate() {
    const token = localStorage.getItem('token');
    const roomNumber = document.getElementById('edit-roomNumber').value;
    const roomType = document.getElementById('edit-roomType').value;
    const capacity = document.getElementById('edit-capacity').value;
    const occupiedBeds = document.getElementById('edit-occupiedBeds').value;

    let apiUrl;
    let requestData;

    if (currentEditingRoomID) {
        // 更新房间
        apiUrl = `${BASE_URL}/room/updateRoom`;
        requestData = {
            roomID: currentEditingRoomID,
            roomNumber: roomNumber,
            roomType: roomType,
            capacity: capacity,
            occupiedBeds: occupiedBeds
        };
    } else {
        // 添加新房间
        apiUrl = `${BASE_URL}/room/insertRoom`;
        requestData = {
            roomNumber: roomNumber,
            roomType: roomType,
            capacity: capacity,
            occupiedBeds: occupiedBeds
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
            alert(currentEditingRoomID ? '更新成功' : '添加成功');
            closeModal();
            loadRoomList(currentPage, pageSize);
        } else {
            alert('保存失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 删除房间
function deleteRoom(roomID) {
    const token = localStorage.getItem('token');

    fetch(`${BASE_URL}/room/deleteRoom?roomID=${roomID}`, {
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
            loadRoomList(currentPage, pageSize);
        } else {
            alert('删除失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 搜索房间
function searchRooms() {
    const token = localStorage.getItem('token');
    const keyword = document.getElementById('search-keyword').value;

    fetch(`${BASE_URL}/room/searchRooms?page=1&size=${pageSize}&keyword=${keyword}`, {
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
            displayRooms(data.data.records);
        } else {
            alert('搜索房间失败: ' + data.message);
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
        loadRoomList(currentPage, pageSize);
    }
}

function nextPage() {
    currentPage++;
    loadRoomList(currentPage, pageSize);
}
