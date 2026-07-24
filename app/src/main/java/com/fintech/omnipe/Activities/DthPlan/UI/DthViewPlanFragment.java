package com.fintech.omnipe.Activities.DthPlan.UI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Activities.DthPlan.dto.DthPlanLanguages;
import com.fintech.omnipe.Activities.DthPlan.dto.PDetails;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanInfoPlan;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanInfoRPData;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanInfoRPWithPackage;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanInfoRecords;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanRPResponse;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.List;


public class DthViewPlanFragment extends Fragment {

    RecyclerView recycler_view;
    TextView noData;
    List<PDetails> operatorDetailRNshow=new ArrayList<>();
    ArrayList<PlanInfoPlan> operatorDetailshow = new ArrayList<>();
    ArrayList<PlanRPResponse> operatorDetailRPshow = new ArrayList<>();
    ArrayList<PlanInfoPlan> operatorDetailPAshow = new ArrayList<>();
    ArrayList<DthPlanLanguages> operatorDetailRPLanguageshow = new ArrayList<>();
    PlanInfoRecords response;
    PlanInfoRPData responseRP;
    PlanInfoRecords responsePA;
    PlanInfoRecords responseMyPlan;
    private PlanInfoRPWithPackage responseRPPackage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recharge_plan_fragment, container, false);

        try {
            String re_type = getArguments().getString("type");
            response = (PlanInfoRecords) getArguments().getSerializable("response");
            responseRP = (PlanInfoRPData) getArguments().getSerializable("responseRP");
            responsePA = (PlanInfoRecords) getArguments().getSerializable("responsePA");
            responseMyPlan = (PlanInfoRecords) getArguments().getSerializable("responseMyPlan");
            responseRPPackage = (PlanInfoRPWithPackage) getArguments().getSerializable("responseRPPackage");
            recycler_view = v.findViewById(R.id.recycler_view);
            noData = v.findViewById(R.id.noData);

            if (response != null) {
                if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailshow.addAll(response.getPlan());
                } else {
                    operatorDetailshow.addAll(response.getAddOnPack());
                }
            }
            else if (responseRP != null) {
                if (responseRP.getResponse() != null && responseRP.getResponse().size() > 0) {
                    for (int i = 0; i < responseRP.getResponse().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRP.getResponse().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRP.getResponse().get(i));
                        }
                    }

                }
                /*if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailRPshow.addAll(responseRP.getResponse());
                }*/
            }
            else if (responsePA != null) {
                if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailshow.addAll(responsePA.getPlan());
                } else {
                    operatorDetailshow.addAll(responsePA.getAddOnPack());
                }
            }

            if (responseMyPlan != null) {
                if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailshow.addAll(responseMyPlan.getPlan());
                } else {
                    operatorDetailshow.addAll(responseMyPlan.getAddOnPack());
                }
            }

            if (responseRPPackage != null) {
                if (responseRPPackage.getResponse() != null && responseRPPackage.getResponse().size() > 0) {
                    for (int i = 0; i < responseRPPackage.getResponse().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRPPackage.getResponse().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRPPackage.getResponse().get(i));
                        }
                    }

                }
                if (responseRPPackage.getPackageList() != null && responseRPPackage.getPackageList().size() > 0) {
                    for (int i = 0; i < responseRPPackage.getPackageList().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRPPackage.getPackageList().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRPPackage.getPackageList().get(i));
                        }
                    }

                }if (responseRPPackage.getLanguages() != null && responseRPPackage.getLanguages().size() > 0) {
                    if (re_type.equalsIgnoreCase("Language")) {
                        operatorDetailRPLanguageshow.addAll(responseRPPackage.getLanguages());
                    }

                }

            }

            if (operatorDetailshow != null && operatorDetailshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListAdapter mAdapter = new DthPlanListAdapter(operatorDetailshow, getActivity());

                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            }
            else if (operatorDetailRPshow != null && operatorDetailRPshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListRPAdapter mAdapter = new DthPlanListRPAdapter(operatorDetailRPshow, getActivity());

                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            }else if (operatorDetailPAshow != null && operatorDetailPAshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListAdapter mAdapter = new DthPlanListAdapter(operatorDetailPAshow, getActivity());
                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            }
            else if (operatorDetailRNshow != null && operatorDetailRNshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListRNAdapter mAdapter = new DthPlanListRNAdapter(operatorDetailRNshow, getActivity());
                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            } else if (operatorDetailRPLanguageshow!= null && operatorDetailRPLanguageshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListLanguageAdapter mAdapter = new DthPlanListLanguageAdapter(operatorDetailRPLanguageshow, getActivity());
                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            }
            else {
                noData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


}
