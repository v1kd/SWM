$app = angular.module('sportz', ['ngRoute']);
$app.config(appRouter);

function appRouter($routeProvider) {
    $routeProvider
        .when('/', {
            template: '<h2>Hello world</h2>',
            controller: function($scope) {
                $scope.setActive('home');
                $scope.appList = {};
                console.log($scope.appList);
            }
        })
        .when('/apply',  {
            template: '<h2>XYZ</h2>',
            controller: function($scope) {
                $scope.setActive('apply');
                console.log($scope.appList);
            }
        });
}