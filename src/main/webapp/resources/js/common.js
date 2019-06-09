let initialCostField = document.getElementById('initial_cost');

let checkEmpty = function (key) {
    return ( key.value.length === 0 || key.value === '' )
};

initialCostField.oninput = function () {
    if (this.value.length > 6) {
        this.value = this.value.slice(0,6);
    }
};
