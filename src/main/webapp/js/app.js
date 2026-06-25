(function () {
    var menuBtn = document.querySelector('.topbar__menu-btn');
    if (!menuBtn) return;

    menuBtn.addEventListener('click', function () {
        document.body.classList.toggle('sidebar-open');
    });

    document.addEventListener('click', function (event) {
        if (!document.body.classList.contains('sidebar-open')) return;
        var sidebar = document.querySelector('.sidebar');
        if (sidebar && !sidebar.contains(event.target) && !menuBtn.contains(event.target)) {
            document.body.classList.remove('sidebar-open');
        }
    });
})();
