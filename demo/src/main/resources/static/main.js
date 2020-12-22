window.onload = function() {
    $(".change-comment").submit((e) => {
        e.preventDefault();
        var postId = $(".change-comment").attr("data-count-id");
        var comment = $(".comment").val();

        $.ajax({
            url: window.location.href,
            type: 'PUT',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data: {"comment" : comment},
            success: function(result) {
                alert("ready");
            }
        });
        //'comment=' + encodeURIComponent(comment)
    });
};