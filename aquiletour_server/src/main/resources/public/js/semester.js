function initializeSemester(viewRootElement, jSweet){

    const weekOfInputs = viewRootElement.find(".week-of-input");
    const currentSemesterCheckboxes = viewRootElement.find(".current-semester-checkbox");

    weekOfInputs.datepicker({
            'closeText': "Fermer",
            'prevText': "Précédent",
            'nextText': "Suivant",
            'currentText': "Aujourd'hui",
            'monthNames': [ "janvier", "février", "mars", "avril", "mai", "juin",
                        "juillet", "août", "septembre", "octobre", "novembre", "décembre" ],
            'monthNamesShort': [ "janv.", "févr.", "mars", "avril", "mai", "juin",
                        "juil.", "août", "sept.", "oct.", "nov.", "déc." ],
            'dayNames': [ "dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi" ],
            'dayNamesShort': [ "dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam." ],
            'dayNamesMin': [ "D", "L", "M", "M", "J", "V", "S" ],
            'weekHeader': "Sem.",
            'dateFormat': "dd/mm/yy",
            'firstDay': 0,
            'isRTL': false,
            'showMonthAfterYear': false,
            'yearSuffix': ""
    });

    currentSemesterCheckboxes.on('click', function(){
        const thisCheckbox = $(this);
        const formId = $(this).attr("form");
        const form = viewRootElement.find("#" + formId);
        form.submit();
    });
}

