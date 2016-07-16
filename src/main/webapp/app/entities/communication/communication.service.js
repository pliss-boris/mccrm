(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('Communication', Communication);

    Communication.$inject = ['$resource', 'DateUtils'];

    function Communication ($resource, DateUtils) {
        var resourceUrl =  'api/communications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
