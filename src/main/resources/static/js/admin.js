function updateFormAction() {
        const selectedPath = document.getElementById('pageRedirect').value;
        
        const form = document.querySelector('form');
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'targetUrl';
        hiddenInput.value = selectedPath;
        form.appendChild(hiddenInput);
    }

window.addEventListener('scroll', () => {
    const nav = document.querySelector('.navbar');
    if (window.scrollY > 50) {
        nav.style.background = '#000';
    } else {
        nav.style.background = 'rgba(0, 0, 0, 0.9)';
    }
});



