appModule.factory('editTitleWindowService', ['$q', '$modal', function($q, $modal){

  return {
    showEditTitle : function(nowTitle) {
      var deferred = $q.defer();
      var modalInstance = $modal.open({
        templateUrl: "template/edit_window.html",
        controller: 'editTitleController',
        resolve: {
          config: function () {
            return {inputLabel:"Title", inputVal:nowTitle}
          }
        }
      });
      modalInstance.result.then(function (newTitle) {
        //by close
        deferred.resolve(newTitle);
      }, function () {
        //by dismiss
        deferred.reject();
      });
      return deferred.promise;
    }
  }
}]);
