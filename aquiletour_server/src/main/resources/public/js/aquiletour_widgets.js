function initializeWidgets(viewRootElement){
    initializeSectionToggles(viewRootElement);
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
