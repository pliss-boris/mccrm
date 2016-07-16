(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('ClassOfService', ClassOfService);

    ClassOfService.$inject = ['$resource', 'DateUtils'];

    function ClassOfService ($resource, DateUtils) {
        var resourceUrl =  'api/class-of-services/:id';

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
