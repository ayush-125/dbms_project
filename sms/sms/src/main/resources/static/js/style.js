{/* <script> */}
        // Toggle visibility of nested lists
        const caretElements = document.querySelectorAll('.caret');
        caretElements.forEach(caret => {
            caret.addEventListener('click', function () {
                const nestedList = this.closest('li').querySelector('.nested');
                nestedList.classList.toggle('active'); // Show/hide employee/user list
            });
        });
        $(document).ready(function() {
    $('.alert').each(function() {
        if ($(this).hasClass('show')) {
            $(this).css('opacity', '1').css('transform', 'translateY(0)');
        }
    });

    // Automatically hide alert after 3 seconds
    setTimeout(function() {
        $('.alert').fadeOut(500, function() {
            $(this).css('transform', 'translateY(-20px)').removeClass('show');
        });
    }, 3000); // Alert will close after 3 seconds
});



    


