$('#rangestart').calendar({
    type: 'date',
    endCalendar: $('#rangeend')
});
$('#rangeend').calendar({
    type: 'date',
    startCalendar: $('#rangestart')
});



$("#startCount").on("click", function (evt) {
    var $el = $("#el"),
        value = 56.4;

    evt.preventDefault();

    $({
        percentage: 0
    }).stop(true).animate({
        percentage: value
    }, {
        duration: 2000,
        easing: "easeOutExpo",
        step: function () {
            // percentage with 1 decimal;
            var percentageVal = Math.round(this.percentage * 10) / 10;

            $el.text(percentageVal + '%');
        }
    }).promise().done(function () {
        // hard set the value after animation is done to be
        // sure the value is correct
        $el.text(value + "%");
    });
});
