'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('proposal', {
                parent: 'entity',
                url: '/proposals',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.proposal.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proposal/proposals.html',
                        controller: 'ProposalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proposal');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('proposal.detail', {
                parent: 'entity',
                url: '/proposal/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.proposal.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proposal/proposal-detail.html',
                        controller: 'ProposalDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proposal');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Proposal', function($stateParams, Proposal) {
                        return Proposal.get({id : $stateParams.id});
                    }]
                }
            })
            .state('proposal.new', {
                parent: 'proposal',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposal/proposal-dialog.html',
                        controller: 'ProposalDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    checkin: null,
                                    checkout: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('proposal', null, { reload: true });
                    }, function() {
                        $state.go('proposal');
                    })
                }]
            })
            .state('proposal.edit', {
                parent: 'proposal',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposal/proposal-dialog.html',
                        controller: 'ProposalDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Proposal', function(Proposal) {
                                return Proposal.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proposal', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('proposal.delete', {
                parent: 'proposal',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposal/proposal-delete-dialog.html',
                        controller: 'ProposalDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Proposal', function(Proposal) {
                                return Proposal.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proposal', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
