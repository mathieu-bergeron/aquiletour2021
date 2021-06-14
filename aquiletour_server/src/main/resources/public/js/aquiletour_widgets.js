function initializeWidgets(viewRootElement){
    initializeCheckboxes(viewRootElement);
    initializeSectionToggles(viewRootElement);
    initializeDatepickers(viewRootElement);
}

function initializeDatepickers(rootElement){

    const datepickers = rootElement.find(".aquiletour-datepicker");

    datepickers.datepicker({
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

}

function initializeDropdownMenus(rootElement){
    const menus = rootElement.find(".dropdown-menu");

    menus.on('hide.bs.dropdown', function(){
        const thisMenu = $(this);
        const toCollapse = thisMenu.find(".collapse");

        toCollapse.each(function(index, item){
            $(item).collapse("hide");
        });
    });
}

function initializeClickBlocker(rootElement){
    const elements = rootElement.find(".block-click-events");

    elements.off();
    elements.on('click', function(){
        return false;
    });
}

function initializeCheckboxes(rootElement){

    const checkboxes = rootElement.find(".aquiletour-checkbox");

    checkboxes.off();
    checkboxes.on('click', function(){
        const thisCheckbox = $(this);

        if(thisCheckbox.attr("checked") === undefined || thisCheckbox.attr("checked") === false){
            thisCheckbox.val("on");
            thisCheckbox.attr("checked","true");
        }else{
            thisCheckbox.val("off");
            thisCheckbox.removeAttr("checked");
        }

        const formId = $(this).attr("form");
        const form = rootElement.find("#" + formId);
        form.submit();
    });
}



const caretUp = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-up" viewBox="0 0 16 16">
  <path d="M3.204 11h9.592L8 5.519 3.204 11zm-.753-.659 4.796-5.48a1 1 0 0 1 1.506 0l4.796 5.48c.566.647.106 1.659-.753 1.659H3.204a1 1 0 0 1-.753-1.659z"/>
  </svg>
`;

const caretDown = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-down" viewBox="0 0 16 16">
      <path d="M3.204 5h9.592L8 10.481 3.204 5zm-.753.659 4.796 5.48a1 1 0 0 0 1.506 0l4.796-5.48c.566-.647.106-1.659-.753-1.659H3.204a1 1 0 0 0-.753 1.659z"/>
    </svg>
`;

function initializeCollapse(rootElement){

    const collapseLinks = rootElement.find(".aquiletour-collapse");

    collapseLinks.each(function(index, element){
        const collapseLink = $(element);
        const collapseTarget = rootElement.find(collapseLink.attr('data-target'));

        collapseLink.off();
        collapseLink.on('click', function(){
            collapseTarget.collapse("toggle");
            return false;
        });
    });
}


function initializeSectionToggles(rootElement){

    const toggles = rootElement.find(".section-toggle");

    toggles.each(function(index, element){
        const toggle = $(element);
        const collapseTarget = rootElement.find(toggle.attr('data-target'));

        updateSectionToggle(toggle);

        toggle.off();
        toggle.on('click', function(){
            collapseTarget.collapse("toggle");
            toggleSectionToggle(toggle);

            return false;
        });
    });
}

function toggleSectionToggle(toggle){
    if(toggle.hasClass('section-collapsed')){

        toggle.removeClass('section-collapsed');

    }else{

        toggle.addClass('section-collapsed');
    }

    updateSectionToggle(toggle);
}

function updateSectionToggle(toggle){
    if(toggle.hasClass('section-collapsed')){

        toggle.find(".bi").remove();
        toggle.append(caretDown);

    }else{

        toggle.find(".bi").remove();
        toggle.append(caretUp);
    }
}
