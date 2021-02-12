
window.onload = function(){
    //for table
    function deleteRow(r) {
      var i = r.parentNode.parentNode.rowIndex;
      document.getElementById("queueTable").deleteRow(i);
    }

    //for list-group
    function fnDelete(obj) {
        obj.closest("li").remove()
    }

    $( function() {
        $( "#appointment-list" ).sortable();
        $( "#appointment-list" ).disableSelection();
    } );
}

