const BASE_URL = 'http://8.134.189.141:8080';
document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    if (!token) {
        alert('您尚未登录，请先登录');
        window.location.href = 'login.html';
        return;
    }

    let profileUrl;
    if (role === 'Doctor') {
        profileUrl = `${BASE_URL}/doctor/getSelf`;
        document.getElementById('role-name').textContent = 'Doctor';
    } else if (role === 'Patient') {
        profileUrl = `${BASE_URL}/patient/getSelf`;
        document.getElementById('role-name').textContent = 'Patient';
    }

    fetch(profileUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            displayProfileData(data.data, role);
        } else {
            alert('获取个人信息失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

function displayProfileData(profileData, role) {
    const infoDiv = document.getElementById('profile-info');
    if (infoDiv) {
        let formHTML = '';

        if (role === 'Doctor') {
            formHTML = `
                <p><span>Name:</span> <input type="text" id="name" value="${profileData.name}" disabled></p>
                <p><span>Contact Number:</span> <input type="text" id="contactNumber" value="${profileData.contactNumber || ''}" disabled></p>
                <p><span>Email:</span> <input type="text" id="email" value="${profileData.email || ''}" disabled></p>
                <p><span>Gender:</span> <input type="text" id="gender" value="${profileData.gender || ''}" disabled></p>
                <p><span>Department ID:</span> <input type="text" id="departmentID" value="${profileData.departmentID || ''}" disabled></p>
            `;
        } else if (role === 'Patient') {
            formHTML = `
                <p><span>Name:</span> <input type="text" id="name" value="${profileData.name}" disabled></p>
                <p><span>Contact Number:</span> <input type="text" id="contactNumber" value="${profileData.contactNumber || ''}" disabled></p>
                <p><span>Gender:</span> <input type="text" id="gender" value="${profileData.gender || ''}" disabled></p>
                <p><span>Address:</span> <input type="text" id="address" value="${profileData.address || ''}" disabled></p>
                <p><span>Date of Birth:</span> <input type="date" id="dateOfBirth" value="${profileData.dateOfBirth || ''}" disabled></p>
                <p><span>Room ID:</span> <input type="text" id="roomID" value="${profileData.roomID || ''}" disabled></p>
            `;
        }

        infoDiv.innerHTML = formHTML;
    }
}

function enableEdit() {
    const inputs = document.querySelectorAll('#profile-info input:not(#roomID)');  // Exclude the roomID input
    inputs.forEach(input => input.disabled = false);
    document.getElementById('save-button').style.display = 'block';
}

function saveProfile() {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    let updateUrl;
    let updateData = {};

    if (role === 'Doctor') {
        updateUrl = `${BASE_URL}/doctor/updateDoctor`;
        updateData = {
            name: document.getElementById('name').value,
            departmentID: document.getElementById('departmentID').value,
            gender: document.getElementById('gender').value,
            email: document.getElementById('email').value,
            contactNumber: document.getElementById('contactNumber').value,
        };
    } else if (role === 'Patient') {
        updateUrl = `${BASE_URL}/patient/updatePatient`;
        updateData = {
            name: document.getElementById('name').value,
            address: document.getElementById('address').value,
            contactNumber: document.getElementById('contactNumber').value,
            gender: document.getElementById('gender').value,
            dateOfBirth: document.getElementById('dateOfBirth').value,
        };
    }

    fetch(updateUrl, {
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
            window.location.reload();
        } else {
            alert('更新失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
