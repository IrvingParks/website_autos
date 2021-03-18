$(function() {
    $('#marca').keyup(function(event) {
        var cars = $(this).val();

        if(cars.length > 0) {
            $.get('Autos?accion=GET_MARCAS&cars='+cars, function(data) {
                $("#marca").autocomplete({
                    source: data.split(",")
                });
            });
        }
    });
});
            
function verGrilla() {
    window.location = "Autos?accion=QRY_ALQ&marca=" + $("#marca").val();
}
