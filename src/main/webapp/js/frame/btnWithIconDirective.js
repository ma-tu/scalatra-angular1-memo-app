appModule.directive('btnWithIconDirective',function(){
  return{
    scope: {
      glyphIcon:'@',
      clickFunc:'&'
    },
    restrict: 'E',
    replace: true,
    template: '<button type="button" class="btn btn-default btn-sm" ng-click="clickFunc()"><span class="glyphicon {{glyphIcon}}"></span></button>'
  }
});