appModule.factory('httpService', ['$http', '$q', function($http, $q){
  var connectEnable = true;
  var connectMessage = "";

  var updateNormal = function(message) {
    connectEnable = true;
    connectMessage = message;
  };

  var updateFail = function(message) {
    connectEnable = false;
    connectMessage = message;
  };

  var httpPost = function(postRequest){
    var deferred = $q.defer();

    if (connectEnable == false){
      deferred.reject();
      return deferred.promise;
    }

    updateNormal(postRequest.message);

    $http({
      method: 'POST',
      url: postRequest.url,
      data: postRequest.data
    }).success(function (data) {
        //成功
        updateNormal("");
        deferred.resolve(data);
      }).error(function () {
        //失敗
        updateFail("Disconnect..");
        deferred.reject();
      });
    return deferred.promise;
  };

  return {
    getConnectEnable : function() {
      return connectEnable;
    },

    getConnectMessage : function() {
      return connectMessage;
    },

    loadNoteList : function() {
      var deferred = $q.defer();
      httpPost(
        {
          url: './api/list',
          message: 'Loading..',
          data: {}
        }
      ).then(
        function(data) {
          var noteList = angular.fromJson(data);
          deferred.resolve(noteList);
        },
        function() {
          deferred.reject();
        }
      );
      return deferred.promise;
    },

    getNewNoteId : function() {
      var deferred = $q.defer();
      httpPost(
        {
          url: './api/newid',
          message: 'Getting..',
          data: {}
        }
      ).then(
        function(data) {
          var json = angular.fromJson(data);
          var newId = json.id;
          deferred.resolve(newId);
        },
        function() {
          deferred.reject();
        }
      );
      return deferred.promise;
    },

    saveChangeList : function(changeListArray) {
      var deferred = $q.defer();
      httpPost(
        {
          url: './api/save',
          message: 'Saving..',
          data: changeListArray
        }
      ).then(
        function() {
          deferred.resolve();
        },
        function() {
          deferred.reject();
        }
      )
      return deferred.promise;
    }
  };
}]);
