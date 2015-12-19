appModule.controller('headerController', ['$scope', "$rootScope", "constEvent", "editChangePasswordWindowService", function ($scope, $rootScope, constEvent, editChangePasswordWindowService) {
  $scope.onAddNote = function () {
    $rootScope.$broadcast(constEvent.EVENT_ADD_NOTE, "");
  };

  $scope.onDeleteNote = function () {
    $rootScope.$broadcast(constEvent.EVENT_DEL_NOTE, "");
  };

  $scope.onPasswordChange = function () {
    editChangePasswordWindowService.showChangePassword();
  };

  $scope.onLogout = function () {
    location.href = "logout";
  };
}]);

