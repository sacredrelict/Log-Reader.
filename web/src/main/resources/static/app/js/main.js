'use strict';

var app = angular.module('main', [
    'ngAnimate',
    'ui.router',
    'ui.bootstrap'
]);

app.run(['$rootScope', '$state',
    function ($rootScope, $state) {
        $rootScope.$state = $state;
        $rootScope.activeTab;
    }
]);

app.config(
    function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('logs', {
                url: '/',
                templateUrl: 'app/view/logs.html',
                controller: 'LogsCtrl'
            })
            .state('about', {
                url: '/about',
                templateUrl: 'app/view/about.html',
                controller: 'AboutCtrl'
            })
    }
);

app.factory('DataService', ['$http',
    function ($http) {
        return {
            getLogs: function (dataFrom, dataSize) {
                return $http.get('/log?from=' + dataFrom + '&size=' + dataSize + '').then(function (response) {
                    return response.data;
                });
            },
            getLogsLevelInfoCount: function () {
                return $http.get('/log/level/info/count').then(function (response) {
                    return response.data;
                });
            },
            getLogsLevelWarningCount: function () {
                return $http.get('/log/level/warning/count').then(function (response) {
                    return response.data;
                });
            },
            getLogsLevelErrorCount: function () {
                return $http.get('/log/level/error/count').then(function (response) {
                    return response.data;
                });
            }
        }
    }
]);

app.controller('LogsCtrl', ['$rootScope', '$scope', 'DataService', '$uibModal', '$location', '$interval',
    function ($rootScope, $scope, DataService, $uibModal, $location, $interval) {
        $rootScope.activeTab = 'logs';
        $scope.error = "";
        $scope.dataFrom = 1;
        $scope.dataSize = 4;
        $scope.showScrollUp = false;
        $scope.showScrollDown = false;

        // Get logs from file and levels count information.
        DataService.getLogs($scope.dataFrom, $scope.dataSize).then(function (data) {
            if (data.error) {
                $scope.error = data.error;
            } else {
                $scope.logs = data;
                $scope.showScrollUp = $scope.logs.hasPrev;
                $scope.showScrollDown = $scope.logs.hasNext;
                $scope.error = $scope.logs.exceptionMessage;
            }
        });

        DataService.getLogsLevelInfoCount().then(function (data) {
            if (data.error) {
                $scope.error = data.error;
            } else {
                $scope.logLevelInfoCount = data;
            }
        });
        DataService.getLogsLevelWarningCount().then(function (data) {
            if (data.error) {
                $scope.error = data.error;
            } else {
                $scope.logLevelWarningCount = data;
            }
        });
        DataService.getLogsLevelErrorCount().then(function (data) {
            if (data.error) {
                $scope.error = data.error;
            } else {
                $scope.logLevelErrorCount = data;
            }
        });

        // Check new lines after every 30 seconds and update levels count information.
        $interval(function() {
            DataService.getLogsLevelInfoCount().then(function (data) {
                if (data.error) {
                    $scope.error = data.error;
                } else {
                    $scope.logLevelInfoCount = data;
                }
            });
            DataService.getLogsLevelWarningCount().then(function (data) {
                if (data.error) {
                    $scope.error = data.error;
                } else {
                    $scope.logLevelWarningCount = data;
                }
            });
            DataService.getLogsLevelErrorCount().then(function (data) {
                if (data.error) {
                    $scope.error = data.error;
                } else {
                    $scope.logLevelErrorCount = data;
                }
            });
        }, 30000);

        // Scroll up function.
        $scope.scrollUp = function() {
            if ($scope.showScrollUp) {
                $scope.dataFrom = $scope.dataFrom - $scope.dataSize;
                DataService.getLogs($scope.dataFrom, $scope.dataSize).then(function (data) {
                    if (data.error) {
                        $scope.error = data.error;
                    } else {
                        $scope.logs = data;
                        $scope.showScrollUp = $scope.logs.hasPrev;
                        $scope.showScrollDown = $scope.logs.hasNext;
                        $scope.error = $scope.logs.exceptionMessage;
                    }
                });
            }
        };

        // Scroll down function.
        $scope.scrollDown = function() {
            if ($scope.showScrollDown) {
                $scope.dataFrom = $scope.dataFrom + $scope.dataSize;
                DataService.getLogs($scope.dataFrom, $scope.dataSize).then(function (data) {
                    if (data.error) {
                        $scope.error = data.error;
                    } else {
                        $scope.logs = data;
                        $scope.showScrollUp = $scope.logs.hasPrev;
                        $scope.showScrollDown = $scope.logs.hasNext;
                        $scope.error = $scope.logs.exceptionMessage;
                    }
                });
            }
        };
    }
]);

app.controller('AboutCtrl', ['$rootScope', '$scope', '$location',
    function ($rootScope, $scope, $location) {
        $rootScope.activeTab = 'about';
    }
]);