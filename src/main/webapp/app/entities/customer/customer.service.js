(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('Customer', Customer);

    Customer.$inject = ['$resource', 'DateUtils'];

    function Customer ($resource, DateUtils) {
        var resourceUrl =  'api/customers/:id';

        return $resource(resourceUrl, {}, {
            'queryWithoutCurrent': {
                method: 'GET',
                isArray: true,
                url: 'api/customers/exclude/:id',
            },
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertLocalDateFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocalDateToServer(data.creationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocalDateToServer(data.creationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
