(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('Subscriber', Subscriber);

    Subscriber.$inject = ['$resource', 'DateUtils'];

    function Subscriber ($resource, DateUtils) {
        var resourceUrl =  'api/subscribers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
