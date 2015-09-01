function AppCtrl($scope) {
    $scope.appList = [
        {
            "name": "Vikranth",
            "type": "Individual"
        },

        {
            "name": "Himanshu",
            "type": "Corporate"
        }
    ];

    $scope.setActive = function(type) {
        $scope.homeActive = '';
        $scope.applyActive = '';
        
        $scope[type + 'Active'] = 'active';
    }
}