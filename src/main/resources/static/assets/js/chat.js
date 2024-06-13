
function sendMessage(message) {
    fetch('/veia/chat/assistant', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({question: message})
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                const messageElement = document.createElement('p');
                // messageElement.textContent = data.answer;
                document.getElementById('chat-messages').appendChild(messageElement);
                typeWriteAnswer(messageElement, data.answer);
            });
        }
    }).catch(error => {
        console.error('Deu ruim:', error);
    })
}
let i= 0;
function typeWriteAnswer(messageElement, message) {
    i=0;
    writer(messageElement, message);
}
function writer(messageElement, message) {
    messageElement.scrollIntoView({ behavior: 'smooth'});
    if (i >= message.length) {
        return;
    }
    setTimeout(() => {
        messageElement.textContent += message.charAt(i);
        i++;
        writer(messageElement, message)
    }, 25);
}
