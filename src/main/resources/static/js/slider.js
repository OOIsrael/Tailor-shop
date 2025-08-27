document.addEventListener('DOMContentLoaded', () => {
    const slider = document.querySelector('.slider');
    const slides = document.querySelectorAll('.slide');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    let index = 0;

    nextBtn.addEventListener('click', () => {
        index = (index + 1) % slides.length;
        slider.style.transform = `translateX(${-index * 100}%)`;
    });

    prevBtn.addEventListener('click', () => {
        index = (index - 1 + slides.length) % slides.length;
        slider.style.transform = `translateX(${-index * 100}%)`;
    });
    // Optional auto-slide
        setInterval(() => {
            index = (index + 1) % slides.length;
            slider.scrollTo({
                left: slides[index].offsetLeft,
                behavior: 'smooth'
            });
        }, 3000);
});