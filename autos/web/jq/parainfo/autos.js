$(function() {
    $("input[type=button], input[type=submit]").button();
    
    // INS
    $("#dins").dialog({
        modal: true,
        autoOpen: false
    });
    //
    
    $("#alquiler").click(function() {
        $("#dins").dialog('open');
    });
    //
    
    $('#fechaalquiler, #fechafinalquiler').datepicker({
        showOn: 'button',
        buttonImage: 'images/calendar.gif',
        buttonImageOnly: true
    });
    
    $('#fechaalquiler').datepicker('setDate', new Date());
    //
    
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
            
function verFotos() {
    $("#cajafotos").load("Autos?accion=GET_FOTOS&marca="+$("#marca").val());
}

function deleteImage( $item ) {
    $item.fadeOut(function() {
        var $list = $( "ul", $trash ).length ?
        $( "ul", $trash ) :
        $( "<ul class='gallery ui-helper-reset'/>" ).appendTo( $trash );
        $item.find( "a.ui-icon-check" ).remove();
        $item.append( recycle_icon ).appendTo( $list ).fadeIn(function() {
            $item
            .animate({
                width: "48px"
            })
            .find( "img" )
            .animate({
                height: "36px"
            });
        });
    });
}
               
function recycleImage( $item ) {
    $item.fadeOut(function() {
        $item
        .find( "a.ui-icon-refresh" )
        .remove()
        .end()
        .end()
        .appendTo( $delete )
        .fadeIn();
    });
}

function valida() {
    var ids = [];

    $("#trash input[name='id']").each(function() {
        ids.push($(this).val());
    });

    if(ids.length == 0) {
        alert("Debe seleccionar auto(s) para Alquilar");
        return false;
    } else {
        $("#ids").val(ids);
    }
    
    return true;
}