appModule.controller('noteController', ['$scope', 'constEvent', 'noteOperatorService', 'noteModelService', 'editTitleWindowService', function ($scope, constEvent, noteOperatorService, noteModelService, editTitleWindowService) {

  var assignEventHandlers = function() {
    $scope.$on(constEvent.EVENT_ADD_NOTE, function(event, data) {
      addNote();
    });
    $scope.$on(constEvent.EVENT_DEL_NOTE, function(event, data) {
      deleteNote();
    });
  };

  var addNote = function() {
    editTitleWindowService.showEditTitle("").then(
      function(newTitle){
        noteOperatorService.addNote(newTitle).then(
          function() {
            $scope.noteText = noteModelService.getSelectedNoteText();
          }
        );
      }
    );
  }

  var deleteNote = function () {
    noteOperatorService.deleteNote();
    $scope.noteText = noteModelService.getSelectedNoteText();
  };

  //----- two way binding -----
  $scope.noteText = "";

  //----- one way binding -----
  $scope.noteList = function() {
    return noteModelService.getNoteList();
  };

  $scope.isSelectedRow = function (index) {
    return noteModelService.isSelectedRow(index);
  };

  //----- event -----
  $scope.initialize = function () {
    assignEventHandlers();
    noteOperatorService.loadNoteList().then(
      function(){
        $scope.noteText = noteModelService.getSelectedNoteText();
      }
    );
  };

  $scope.onTitleListClick = function (index) {
    noteModelService.setSelectedRow(index);
    $scope.noteText = noteModelService.getSelectedNoteText();
  };

  $scope.onTextChange = function () {
    noteOperatorService.updateNoteText($scope.noteText);
  };

  $scope.onEditNoteInfo = function () {
    if (noteModelService.hasSelectedNote()) {
      var noteTitle = noteModelService.getSelectedNoteTitle();
      editTitleWindowService.showEditTitle(noteTitle).then(
        function(newTitle) {
          noteOperatorService.updateNoteTitle(newTitle);
        }
      );
    }
  };

}]);

