// roomList.js

let currentPage = 1;
const pageSize = 5;
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

// 显示房间列表
function displayRooms(rooms) {
    const roomListTbody = document.getElementById('room-list');
    roomListTbody.innerHTML = '';

    if (rooms.length === 0) {
        roomListTbody.innerHTML = '<tr><td colspan="4">No rooms found.</td></tr>';
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
        `;
        roomListTbody.appendChild(roomRow);
    });
}

// 分页功能 - 上一页
function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        loadRoomList(currentPage, pageSize);
    }
}

// 分页功能 - 下一页
function nextPage() {
    currentPage++;
    loadRoomList(currentPage, pageSize);
}
